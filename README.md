# Common Library Project

## Overview

The **Common Library** consists of two Android applications designed to help users build healthy daily habits —  
tracking water intake and monitoring smoke-free progress.

Both apps share a common library that manages base activities, utilities, and UI components,  
ensuring a consistent structure while allowing for custom visual experiences in each app.

---

## Applications

### Water Tracking

Helps users track daily water consumption.  
With a clean and simple interface, users can log each glass they drink and monitor progress toward hydration goals.

<p align="center">
  <img src="https://github.com/user-attachments/assets/133b86eb-a9dc-4672-aba3-56d49ef60ed1" width="200"/>
</p>

---

### Smoking Tracking

Supports users on their journey to quit smoking.  
The app tracks smoke-free days, encourages consistency, and celebrates key milestones.

<p align="center">
  <img src="https://github.com/user-attachments/assets/f49ade27-8b99-4bc2-a1a8-3ebdfe62e6f0" width="200"/>
</p>

---

## Features

- **Shared Common Library**  
  - Abstract base activity for both apps  
  - PreferencesHelper for local data storage  
  - Timer utilities (optional)  
  - Shared layouts and reusable UI elements

- **Custom UI per App**  
  Each application has its own visual identity while relying on shared structure and logic.

- **Progress Tracking**  
  - WaterTracking: Animated progress bar for number of glasses consumed  
  - SmokingTracking: Tracks smoke-free days with visual milestones

- **Achievements**  
  Unlockable achievements at specific milestones to encourage habit-building

- **Reset Functionality**  
  Users can reset their counters at any time in both apps

---

## App Comparison

| Feature                | Water Tracking                         | Smoking Tracking                          |
|------------------------|----------------------------------------|--------------------------------------------|
| Purpose                | Daily water intake monitoring          | Smoke-free days tracking                   |
| Theme Colors           | Pastel blue                            | Dark gray, red, black                      |
| Main Button Text       | Add Glass                              | No Smoking Today                           |
| Reset Button Text      | Reset Counter                          | Smoked - Reset                             |
| Progress Type          | Glasses per day                        | Consecutive smoke-free days                |
| Achievements           | 5, 8, 12 glasses                       | 1, 5, 10, 30 days                          |
| Lottie Animation       | Triggered at milestones                | Triggered at milestones                    |
| Feedback               | Visual + motivational message          | Visual + vibration                         |
| Design Style           | Minimalist, soft colors                | Bold, high-contrast colors                 |

---

## Usage

- **Water Tracking:**  
  Press “Add Glass” to log your intake, track daily hydration, and unlock visual achievements.

- **Smoking Tracking:**  
  Press “No Smoking Today” each smoke-free day to increase your streak and celebrate your progress.

---
