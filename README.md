# 2DGameJAVA

A Java-based 2D game project designed for learning and experimentation in game development. This repository contains the full source code, assets, and documentation for building and running a simple 2D game using Java.

## Features

- Basic 2D game loop and rendering
- Player movement and controls
- Collision detection
- Simple graphical assets
- Modular code structure for easy extension

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans) or command line tools

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Carbon14-48/2DGameJAVA.git
   cd 2DGameJAVA
   ```

2. **Open in your preferred IDE** or compile using the command line:
   ```bash
   javac -d bin src/**/*.java
   ```

3. **Run the game:**
   ```bash
   java -cp bin Main
   ```
   (Replace `Main` with the actual main class if different.)

## Project Structure

```
2DGameJAVA/
├── src/            # Java source files
├── lib/         # External libraries (images, sounds)
├── bin/            # Compiled class files+ressources
├── maps/           # game maps  
├── config.txt     #contains the saved config            
└── README.md
```

## How to Play

- Use keyboard controls (e.g., arrow keys or WASD) to move the player character.
- Avoid obstacles and interact with the environment as described in the game.
-You can change the map while playing you just have to press R for reloading the map.
-press F to shoot , C to open inventory and ESC to view options .

## Contributing

Contributions are welcome! Feel free to fork this repository and submit pull requests.

1. Fork the repo
2. Create a new branch (`git checkout -b feature-XYZ`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add new feature'`)
5. Push to the branch (`git push origin feature-XYZ`)
6. Create a new Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by classic 2D games and Java game development tutorials.
- Thanks to all contributors and open-source resources.

---
Happy coding and game development!
