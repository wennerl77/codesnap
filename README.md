# 📸 CodeSnap — Beautiful Code Screenshots

[![GitHub stars](https://img.shields.io/github/stars/Senzo13/codesnap?style=flat-square)](https://github.com/Senzo13/codesnap/stargazers)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)

**Create stunning screenshots of your code in seconds.** Free, open-source alternative to [Carbon](https://carbon.now.sh).

No sign-up. No backend. No build step. Just open and go.

---

## ✨ Features

- 🎨 **8 beautiful themes** — Dracula, Monokai, GitHub Dark, One Dark, Nord, Solarized, Night Owl, Tokyo Night
- 🌐 **15 languages** — JavaScript, Python, TypeScript, HTML, CSS, Java, C++, Go, Rust, PHP, Ruby, SQL, Bash, JSON, YAML
- 🌈 **Gradient backgrounds** — Sunset, Ocean, Forest, Midnight, Candy, or pick a custom color
- 📐 **Customizable padding** — 16px, 32px, or 64px
- 🔢 **Line numbers** — Toggle on or off
- 🖥️ **macOS window controls** — Classic red/yellow/green dots or none
- 🔤 **Adjustable font size** — 12px to 20px
- 📏 **Border radius control** — From sharp to rounded corners
- 📸 **Export as PNG** — High-resolution 2x export via html2canvas
- 📋 **Copy to clipboard** — One-click copy as image
- ⚡ **Zero dependencies** — Pure HTML/CSS/JS with CDN libraries
- 🚀 **No build step** — Just open `index.html`

---

## 🚀 How to Use

1. **Paste** your code into the editor
2. **Customize** the theme, language, background, padding, and more
3. **Export** as PNG or copy to clipboard

That's it!

---

## 🎨 Available Themes

| Theme | Style |
|-------|-------|
| Dracula | Dark purple tones |
| Monokai | Classic dark with vibrant colors |
| GitHub Dark | GitHub's dark mode |
| One Dark | Atom's iconic dark theme |
| Nord | Arctic, north-bluish palette |
| Solarized | Ethan Schoonover's dark variant |
| Night Owl | Designed for night owls |
| Tokyo Night | A Tokyo-inspired dark theme |

---

## 🏠 Self-Hosting

CodeSnap is a static site — no server required.

```bash
# Clone the repo
git clone https://github.com/Senzo13/codesnap.git

# Open it
cd codesnap
open index.html    # macOS
start index.html   # Windows
xdg-open index.html # Linux
```

Or simply deploy the folder to any static hosting provider:
- **GitHub Pages** — Push and enable Pages in repo settings
- **Netlify** — Drag and drop the folder
- **Vercel** — Import the repo

---

## 📁 Project Structure

```
codesnap/
├── index.html          # Main HTML page
├── css/
│   ├── style.css       # Core styles & layout
│   └── themes.css      # Code editor theme colors
├── js/
│   ├── app.js          # Main application logic
│   └── export.js       # PNG export & clipboard
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome! Here's how:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 🙏 Credits

- [highlight.js](https://highlightjs.org/) — Syntax highlighting
- [html2canvas](https://html2canvas.hertzen.com/) — Screenshot capture
- [JetBrains Mono](https://www.jetbrains.com/lp/mono/) — Monospace font
- Inspired by [Carbon](https://carbon.now.sh)
