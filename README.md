# Introduction
World Grand Prix is a fictional racing series inspired by the championship presented in the Cars 2 and by the concept of Formula One running carbon-neutral atmospheric V10 engines from 2026 onward. Nine teams, sponsored by global oil companies, compete using modified Ferrari F2002 machinery in events held across every continent. After each season, an intense development war begins. Who will become the legend of this new motorsport circus?

This application manages progression between seasons by reading, creating, and modifying Assetto Corsa game files. In this way, it links together otherwise separate championships provided by the original game.

# Development
The competition platform is the base version of Assetto Corsa (without Content Manager). After an initial analysis performed in Enterprise Architect, I chose a prototype-driven development approach.

An alpha version has already been implemented (see the `main` branch), so I decided not to continue with further formal analysis. The development goals are clear, and the next step is to iterate directly on the prototype. After that, the application will be fully integrated with the game files. Finally, I will design and implement the model governing actions that take place between seasons.