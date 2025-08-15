# MineSweeper Java Application

## Overview

This is a **console-based Minesweeper game** implemented in Java. The game supports both square and rectangular boards, and allows the player to uncover cells until they either reveal all safe spots or hit a mine.

### 🎯 Main Features

- **Board Types**:
    - `S` — Square board (N × N)
    - `R` — Rectangular board (Rows × Columns)

- **Randomized Mines**: Mines are placed randomly at the beginning of the game.
- **Adjacency Logic**: Each cell stores the count of adjacent mines.
- **Game Loop**: User selects cells until they win or hit a mine.
- **Automatic Reveal**: Zero-adjacent-mine cells reveal recursively.
- **Board Display**: Clearly shows revealed and unrevealed cells. Mines are shown upon game end.

---

## 🧱 Design and Assumptions

### 📦 Classes

- `Game`: Main entry point. Manages input, board creation, and game loop.
- `Board`: Represents the Minesweeper board. Handles mines, cell logic, and printing.
- `Cell`: Represents a single cell — mine status, reveal state, and adjacent mine count.

### 📌 Assumptions

- Max number of mines = **35%** of total cells.
- Inputs are validated with prompts for invalid entries.
- Cell selection uses row letters and column numbers (e.g., A1, C3).
- Diagonal neighbors are considered for adjacency.
- Both square and rectangular board shapes are supported.

---

## ⌨️ Input Pattern

- **Board Type**: S or R
- **Board Size**:
    - S → Single integer `N`
    - R → Two integers `rows,cols` (e.g., `4,6`)
- **Number of Mines**: Integer ≤ 35% of total cells
- **Cell Clicks**: Format like `A1`, `C3`, etc.

---

📁 Project Structure

MineSweeperGame/

├── libs.zip                  # External JAR libraries

├── src/

│   └── com/

│   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  └── game/

│      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     ├── Board.java

│      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     ├── Cell.java

│       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    └── Game.java

└── test/                   #  Test files for each of above class

---
## 💻 How to Compile and Run

### ✅ Environment

- Java Development Kit (JDK) **8 or above**
- Works on Windows / macOS / Linux
- Terminal or command prompt access

---

### 🧪 Compilation Instructions

1. Open a terminal and **navigate to the project root**:

cd MineSweeperGame

Create the output directory (if not already present):


mkdir -p bin

Compile the code (with libraries if needed):


javac -cp "libs/*" -d bin src/com/game/*.java

libs.zip contains any required .jar libraries(Java and Junit 5.8.1)

Compiled .class files go to the bin/ folder.

▶️ Run the Game

java -cp "bin:libs/*" com.game.Game         # macOS/Linux

java -cp "bin;libs/*" com.game.Game         # Windows

📦 **External Libraries**
✅ _All required libraries are located in the libs.zip folder. No external downloads are necessary — just extarct the folder and  include them via the -cp "libs/*" option during compile and run.
We need java version 8 or above and Junit 5.8.1. Please include them in class path from
lips.zip  folder in the project._

##Example Session

Square or rectangular board? (S/R): S

Enter size for square board: 5

Enter number of mines (max 8): 6

1  2  3  4  5

A  _ _ _ _ _

B  _ _ _ _ _

C  _ _ _ _ _

D  _ _ _ _ _

E  _ _ _ _ _

Click a cell (e.g., A1): B2
...

***Congratulations*** You won! All safe cells revealed!
