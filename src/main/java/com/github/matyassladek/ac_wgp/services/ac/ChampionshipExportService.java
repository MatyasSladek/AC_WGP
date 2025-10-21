package com.github.matyassladek.ac_wgp.services.ac;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Team;
import com.github.matyassladek.ac_wgp.model.Track;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service responsible for exporting championships to Assetto Corsa .champ format.
 * Writes championship files to the AC user data directory.
 */
public class ChampionshipExportService {

    private static final Logger log = Logger.getLogger(ChampionshipExportService.class.getName());
    private static final String CHAMPIONSHIP_NAME = "Companion app - WGP";
    private static final int MAX_CARS = 20;

    private final ObjectMapper objectMapper;
    private final Random random;

    public ChampionshipExportService() {
        this(new ObjectMapper(), new Random());
    }

    public ChampionshipExportService(ObjectMapper objectMapper, Random random) {
        this.objectMapper = objectMapper;
        this.random = random;
    }

    /**
     * Exports the championship to a .champ file in the AC user data directory.
     *
     * @param game The game instance containing championship data
     * @param calendar The calendar (list of tracks) for the championship
     * @return The path to the created championship file
     * @throws IOException if export fails
     */
    public Path exportChampionship(Game game, List<Track> calendar) throws IOException {
        if (game == null || calendar == null || calendar.isEmpty()) {
            throw new IllegalArgumentException("Game and calendar cannot be null or empty");
        }

        // Get the champs directory from the race_out.json path
        Path champsDir = getChampsDirectory(game);
        ensureDirectoryExists(champsDir);

        // Generate a unique filename
        String filename = generateChampionshipFilename();
        Path champFilePath = champsDir.resolve(filename);

        // Create the championship data structure
        Map<String, Object> championshipData = buildChampionshipData(game, calendar);

        // Write to file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(champFilePath.toFile(), championshipData);
        log.info("Championship exported successfully to: " + champFilePath);

        return champFilePath;
    }

    /**
     * Gets the champs directory from the race_out.json path.
     * race_out.json is at: {UserData}/out/race_out.json
     * champs directory is at: {UserData}/champs/
     *
     * @param game The game instance
     * @return Path to the champs directory
     */
    private Path getChampsDirectory(Game game) {
        String raceOutPath = game.getJsonResultsPath();
        if (raceOutPath == null || raceOutPath.isEmpty()) {
            throw new IllegalStateException("Race out JSON path is not configured");
        }

        // Get parent directory of 'out' folder
        Path raceOutFile = Paths.get(raceOutPath);
        Path userDataDir = raceOutFile.getParent().getParent();

        return userDataDir.resolve("champs");
    }

    /**
     * Ensures the directory exists, creates it if it doesn't.
     *
     * @param directory The directory path
     * @throws IOException if directory creation fails
     */
    private void ensureDirectoryExists(Path directory) throws IOException {
        File dir = directory.toFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Failed to create directory: " + directory);
            }
            log.info("Created champs directory: " + directory);
        }
    }

    /**
     * Generates a unique championship filename with UUID.
     *
     * @return Filename in format: {UUID}.champ
     */
    private String generateChampionshipFilename() {
        String uuid = UUID.randomUUID().toString();
        return uuid + ".champ";
    }

    /**
     * Builds the complete championship data structure for JSON export.
     *
     * @param game The game instance
     * @param calendar The calendar for the championship
     * @return Map representing the championship data
     */
    private Map<String, Object> buildChampionshipData(Game game, List<Track> calendar) {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", CHAMPIONSHIP_NAME);
        data.put("rules", buildRules(game));
        data.put("opponents", buildOpponents(game));
        data.put("rounds", buildRounds(calendar));
        data.put("maxCars", String.valueOf(MAX_CARS));

        return data;
    }

    /**
     * Builds the rules section of the championship.
     *
     * @param game The game instance
     * @return Map containing championship rules
     */
    private Map<String, Object> buildRules(Game game) {
        Map<String, Object> rules = new LinkedHashMap<>();

        rules.put("practice", 0);
        rules.put("qualifying", 5);
        rules.put("points", Arrays.asList(20, 15, 12, 9, 7, 5, 4, 3, 2, 1));
        rules.put("penalties", false);
        rules.put("jumpstart", 2);

        return rules;
    }

    /**
     * Builds the opponents list (all drivers except the player).
     *
     * @param game The game instance
     * @return List of opponent data
     */
    private List<Map<String, Object>> buildOpponents(Game game) {
        List<Map<String, Object>> opponents = new ArrayList<>();

        Driver player = game.getPlayer();
        Team playerTeam = findPlayerTeam(game, player);

        // Add PLAYER as the first entry
        if (playerTeam != null) {
            opponents.add(buildPlayerData(player, playerTeam));
        }

        for (Team team : game.getTeams()) {
            // Add driver 1 if not the player
            if (!team.getDriver1().equals(player)) {
                opponents.add(buildOpponentData(team.getDriver1(), team));
            }

            // Add driver 2 if not the player
            if (!team.getDriver2().equals(player)) {
                opponents.add(buildOpponentData(team.getDriver2(), team));
            }
        }

        return opponents;
    }

    private Team findPlayerTeam(Game game, Driver player) {
        for (Team team : game.getTeams()) {
            if (team.getDriver1().equals(player) || team.getDriver2().equals(player)) {
                return team;
            }
        }
        return null;
    }

    private Map<String, Object> buildPlayerData(Driver player, Team team) {
        Map<String, Object> playerData = new LinkedHashMap<>();

        // Determine which skin to use based on driver position in team
        String skin = player.equals(team.getDriver1())
                ? team.getManufacture().getSkinDriver1()
                : team.getManufacture().getSkinDriver2();

        playerData.put("name", "PLAYER");
        playerData.put("nation", ""); // Empty nation for player
        playerData.put("car", team.getManufacture().getCar());
        playerData.put("skin", skin);
        playerData.put("ballast", 0);
        playerData.put("restrictor", 0);

        return playerData;
    }

    /**
     * Builds opponent data for a single driver.
     *
     * @param driver The driver
     * @param team The team
     * @return Map containing opponent data
     */
    private Map<String, Object> buildOpponentData(Driver driver, Team team) {
        Map<String, Object> opponent = new LinkedHashMap<>();

        // Determine which skin to use based on driver position in team
        String skin = driver.equals(team.getDriver1())
                ? team.getManufacture().getSkinDriver1()
                : team.getManufacture().getSkinDriver2();

        // Calculate penalties based on team performance
        int ballast = team.getChassis().getPerformance();
        int restrictor = team.getEngine().getPerformance();

        opponent.put("name", driver.getName());
        opponent.put("nation", driver.getCountry().getCode());
        opponent.put("car", team.getManufacture().getCar());
        opponent.put("skin", skin);
        opponent.put("ballast", ballast);
        opponent.put("restrictor", restrictor);

        return opponent;
    }

    /**
     * Builds the rounds (races) list from the calendar.
     *
     * @param calendar The list of tracks
     * @return List of round data
     */
    private List<Map<String, Object>> buildRounds(List<Track> calendar) {
        List<Map<String, Object>> rounds = new ArrayList<>();

        for (Track track : calendar) {
            rounds.add(buildRoundData(track));
        }

        return rounds;
    }

    /**
     * Builds round data for a single track.
     *
     * @param track The track
     * @return Map containing round data
     */
    private Map<String, Object> buildRoundData(Track track) {
        Map<String, Object> round = new LinkedHashMap<>();

        round.put("track", track.getIdentifier());
        round.put("laps", track.getLaps());
        round.put("weather", random.nextInt(5)); // 0-4
        round.put("surface", random.nextInt(6)); // 0-5

        return round;
    }
}