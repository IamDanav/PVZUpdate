# Plants vs Zombies 

A mini Plants vs Zombies clone developed using **Java Swing** and **Object-Oriented Programming (OOP)**.

The game features multiple plant and zombie types, projectile combat, sun economy mechanics, lawn mowers, shovel mechanics, and interactive tower defense gameplay inspired by the original *Plants vs Zombies*.

---

# Features

- Sun economy system
- Multiple plant types
- Multiple zombie types
- Slow effect bullets
- Cherry Bomb explosion
- Potato Mine trap
- Chomper instant kill
- Lawn mower system
- Shovel remove mechanic
- Hover placement preview
- Game Over system

---

# Plants

| Plant | Ability | Color | Cost |
|---|---|---|---|
| Sunflower | Generate sun | Yellow | 50 |
| Peashooter | Shoot peas | Green | 100 |
| SnowPea | Slow zombies | Cyan | 175 |
| Repeater | Double shots | Green | 200 |
| WallNut | High HP defense | Orange | 50 |
| CherryBomb | Area explosion | Red | 150 |
| PotatoMine | Delayed explosive trap | Dark Gray | 25 |
| Chomper | Instantly eats zombies | Magenta | 150 |

---

# Zombies

| Zombie | Ability | Color |
|---|---|---|
| BrownSuit | Basic zombie | Red |
| ConeHead | Higher HP | Orange |
| BucketHead | Tank zombie | Gray |
| Newspaper | Faster when angry | White | 
| PoleVaulting | Jumps over first plant | Black | 

---

# Controls

- Left click plant cards to select plants
- Left click grid to place plants
- Click sun to collect sun points
- Use shovel to remove plants

---

# Technologies

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- Git & GitHub

---

# How To Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Run `Main.java`

---

# Future Improvements

- Wave system
- Animated sprites
- Sound effects
- More maps
- Additional zombie types
- Save/load system

---
# Design Pattern

- Singleton

---
# Screenshots


<img width="1118" height="746" alt="Screenshot 2026-05-02 075600" src="https://github.com/user-attachments/assets/7b658b30-ad50-4981-a1bc-bacaae35a3ae" />

---
# Developed by

- ITITWE24063 - Nguyễn Tấn Phát
- ITITIU24041 - Lê Văn Nhân
- ITITWE24013 - Hoàng Hải Đăng
- ITITIU24001 - Nguyễn Thế Đức Anh

## UI Redesign Update

The Swing interface has been redesigned to look closer to Plants vs Zombies:

- PVZ-style top seed bank with sun counter, seed cards, shovel, and menu label.
- Alternating lawn tiles with grass details.
- Icon-like drawings for plants, zombies, bullets, sun, and lawn mowers using Graphics2D.
- Clear selected-card border and hover preview for planting/removing plants.
- The main window now uses `pack()`, a fixed preferred size, and opens centered on screen.

Changed files:

- `src/Core/GamePanel.java`
- `src/Map/Grid.java`
- `src/Main.java`
