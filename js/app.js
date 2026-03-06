/* ============================================
   CodeSnap — Main Application Logic
   ============================================ */

(function () {
  'use strict';

  // ---- Theme to highlight.js stylesheet mapping ----
  const HLJS_THEMES = {
    'dracula': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/dracula.min.css',
    'monokai': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/monokai.min.css',
    'github-dark': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css',
    'one-dark': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/atom-one-dark.min.css',
    'nord': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/nord.min.css',
    'solarized': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/base16/solarized-dark.min.css',
    'night-owl': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/night-owl.min.css',
    'tokyo-night': 'https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/tokyo-night-dark.min.css',
  };

  // ---- Gradient presets ----
  const GRADIENTS = {
    'sunset': 'linear-gradient(135deg, #f97316, #ec4899)',
    'ocean': 'linear-gradient(135deg, #3b82f6, #8b5cf6)',
    'forest': 'linear-gradient(135deg, #22c55e, #14b8a6)',
    'midnight': 'linear-gradient(135deg, #1e3a5f, #7c3aed)',
    'candy': 'linear-gradient(135deg, #ec4899, #3b82f6)',
  };

  // ---- Language file extensions ----
  const LANG_EXTENSIONS = {
    'javascript': 'js',
    'python': 'py',
    'typescript': 'ts',
    'html': 'html',
    'css': 'css',
    'java': 'java',
    'cpp': 'cpp',
    'go': 'go',
    'rust': 'rs',
    'php': 'php',
    'ruby': 'rb',
    'sql': 'sql',
    'bash': 'sh',
    'json': 'json',
    'yaml': 'yml',
  };

  // ---- DOM Elements ----
  const elements = {
    codeInput: document.getElementById('code-input'),
    codeOutput: document.getElementById('code-output'),
    languageSelect: document.getElementById('language-select'),
    themeSelect: document.getElementById('theme-select'),
    gradientPresets: document.getElementById('gradient-presets'),
    paddingGroup: document.getElementById('padding-group'),
    borderRadius: document.getElementById('border-radius'),
    borderRadiusValue: document.getElementById('border-radius-value'),
    fontSize: document.getElementById('font-size'),
    fontSizeValue: document.getElementById('font-size-value'),
    windowControlsGroup: document.getElementById('window-controls-group'),
    lineNumbersToggle: document.getElementById('line-numbers-toggle'),
    windowTitle: document.getElementById('window-title'),
    windowTitleDisplay: document.getElementById('window-title-display'),
    windowDots: document.getElementById('window-dots'),
    codeCardBg: document.getElementById('code-card-bg'),
    codeWindow: document.getElementById('code-window'),
    hljsThemeLink: document.getElementById('hljs-theme'),
    customBgColor: document.getElementById('custom-bg-color'),
    customGradientBtn: document.getElementById('custom-gradient-btn'),
  };

  // ---- State ----
  const state = {
    language: 'javascript',
    theme: 'one-dark',
    gradient: 'sunset',
    padding: 64,
    borderRadius: 12,
    fontSize: 14,
    windowControls: 'macos',
    showLineNumbers: true,
    windowTitle: '',
  };

  // ---- Initialize ----
  function init() {
    updateHighlight();
    bindEvents();
    updateTitlePlaceholder();
  }

  // ---- Syntax Highlighting ----
  function updateHighlight() {
    const code = elements.codeInput.value;
    const escaped = escapeHtml(code);

    // Build lines with line number support
    const lines = escaped.split('\n');
    const html = lines.map(line => {
      return `<span class="code-line">${line || ' '}</span>`;
    }).join('\n');

    elements.codeOutput.innerHTML = html;
    elements.codeOutput.className = 'hljs language-' + state.language;

    if (state.showLineNumbers) {
      elements.codeOutput.classList.add('show-line-numbers');
    }

    // Apply highlight.js
    delete elements.codeOutput.dataset.highlighted;
    hljs.highlightElement(elements.codeOutput);

    // Re-wrap in line spans after hljs processes it
    rebuildLineSpans();
  }

  function rebuildLineSpans() {
    const codeEl = elements.codeOutput;
    const rawHtml = codeEl.innerHTML;

    // Split highlighted HTML into lines
    const lines = splitHighlightedLines(rawHtml);

    const html = lines.map(line => {
      return `<span class="code-line">${line || ' '}</span>`;
    }).join('\n');

    codeEl.innerHTML = html;

    // Re-apply line number class
    if (state.showLineNumbers) {
      codeEl.classList.add('show-line-numbers');
    } else {
      codeEl.classList.remove('show-line-numbers');
    }
  }

  function splitHighlightedLines(html) {
    // We need to split the highlighted HTML by newlines while keeping
    // span tags balanced. Simple approach: replace newlines that are
    // outside of tags, then split.
    const lines = [];
    let current = '';
    const openSpans = [];

    let i = 0;
    while (i < html.length) {
      if (html[i] === '\n') {
        // Close open spans for this line
        let closeTags = '';
        for (let j = openSpans.length - 1; j >= 0; j--) {
          closeTags += '</span>';
        }
        lines.push(current + closeTags);

        // Re-open spans for next line
        let openTags = '';
        for (let j = 0; j < openSpans.length; j++) {
          openTags += openSpans[j];
        }
        current = openTags;
        i++;
      } else if (html[i] === '<') {
        // Read the tag
        let tag = '';
        while (i < html.length && html[i] !== '>') {
          tag += html[i];
          i++;
        }
        tag += '>';
        i++;

        if (tag.startsWith('<span')) {
          openSpans.push(tag);
        } else if (tag === '</span>') {
          openSpans.pop();
        }
        current += tag;
      } else {
        current += html[i];
        i++;
      }
    }

    if (current) {
      lines.push(current);
    }

    return lines;
  }

  function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerHTML;
  }

  // ---- Theme Management ----
  function applyTheme(themeName) {
    state.theme = themeName;

    // Update highlight.js stylesheet
    if (HLJS_THEMES[themeName]) {
      elements.hljsThemeLink.href = HLJS_THEMES[themeName];
    }

    // Update CSS class on code card background
    const cardBg = elements.codeCardBg;
    // Remove all theme classes
    Object.keys(HLJS_THEMES).forEach(t => {
      cardBg.classList.remove('theme-' + t);
    });
    cardBg.classList.add('theme-' + themeName);

    // Re-highlight after theme CSS loads
    setTimeout(updateHighlight, 100);
  }

  // ---- Background Gradient ----
  function applyGradient(gradientName) {
    state.gradient = gradientName;

    // Update active state
    document.querySelectorAll('.gradient-btn').forEach(btn => {
      btn.classList.toggle('active', btn.dataset.gradient === gradientName);
    });

    if (GRADIENTS[gradientName]) {
      elements.codeCardBg.style.background = GRADIENTS[gradientName];
    }
  }

  function applyCustomColor(color) {
    state.gradient = 'custom';
    document.querySelectorAll('.gradient-btn').forEach(btn => {
      btn.classList.toggle('active', btn.dataset.gradient === 'custom');
    });
    elements.codeCardBg.style.background = color;
  }

  // ---- Padding ----
  function applyPadding(value) {
    state.padding = parseInt(value);
    elements.codeCardBg.style.padding = value + 'px';
  }

  // ---- Border Radius ----
  function applyBorderRadius(value) {
    state.borderRadius = parseInt(value);
    elements.codeWindow.style.borderRadius = value + 'px';
    elements.borderRadiusValue.textContent = value + 'px';
  }

  // ---- Font Size ----
  function applyFontSize(value) {
    state.fontSize = parseInt(value);
    elements.codeOutput.style.fontSize = value + 'px';
    elements.fontSizeValue.textContent = value + 'px';
  }

  // ---- Window Controls ----
  function applyWindowControls(style) {
    state.windowControls = style;
    if (style === 'none') {
      elements.windowDots.classList.add('hidden');
    } else {
      elements.windowDots.classList.remove('hidden');
    }
  }

  // ---- Line Numbers ----
  function applyLineNumbers(show) {
    state.showLineNumbers = show;
    updateHighlight();
  }

  // ---- Window Title ----
  function updateTitlePlaceholder() {
    const ext = LANG_EXTENSIONS[state.language] || 'txt';
    elements.windowTitle.placeholder = 'untitled.' + ext;
  }

  function applyWindowTitle() {
    const title = elements.windowTitle.value;
    state.windowTitle = title;
    const ext = LANG_EXTENSIONS[state.language] || 'txt';
    elements.windowTitleDisplay.textContent = title || 'untitled.' + ext;
  }

  // ---- Event Binding ----
  function bindEvents() {
    // Code input
    elements.codeInput.addEventListener('input', updateHighlight);

    // Tab key support in textarea
    elements.codeInput.addEventListener('keydown', function (e) {
      if (e.key === 'Tab') {
        e.preventDefault();
        const start = this.selectionStart;
        const end = this.selectionEnd;
        this.value = this.value.substring(0, start) + '  ' + this.value.substring(end);
        this.selectionStart = this.selectionEnd = start + 2;
        updateHighlight();
      }
    });

    // Language
    elements.languageSelect.addEventListener('change', function () {
      state.language = this.value;
      updateTitlePlaceholder();
      applyWindowTitle();
      updateHighlight();
    });

    // Theme
    elements.themeSelect.addEventListener('change', function () {
      applyTheme(this.value);
    });

    // Gradient presets
    elements.gradientPresets.addEventListener('click', function (e) {
      const btn = e.target.closest('.gradient-btn');
      if (!btn) return;

      const gradient = btn.dataset.gradient;
      if (gradient === 'custom') {
        elements.customBgColor.click();
        return;
      }
      applyGradient(gradient);
    });

    // Custom color
    elements.customBgColor.addEventListener('input', function () {
      applyCustomColor(this.value);
    });

    // Padding
    elements.paddingGroup.addEventListener('click', function (e) {
      const btn = e.target.closest('.btn-option');
      if (!btn) return;
      this.querySelectorAll('.btn-option').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      applyPadding(btn.dataset.value);
    });

    // Border radius
    elements.borderRadius.addEventListener('input', function () {
      applyBorderRadius(this.value);
    });

    // Font size
    elements.fontSize.addEventListener('input', function () {
      applyFontSize(this.value);
    });

    // Window controls
    elements.windowControlsGroup.addEventListener('click', function (e) {
      const btn = e.target.closest('.btn-option');
      if (!btn) return;
      this.querySelectorAll('.btn-option').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      applyWindowControls(btn.dataset.value);
    });

    // Line numbers
    elements.lineNumbersToggle.addEventListener('change', function () {
      applyLineNumbers(this.checked);
    });

    // Window title
    elements.windowTitle.addEventListener('input', applyWindowTitle);
  }

  // ---- Start ----
  document.addEventListener('DOMContentLoaded', function () {
    init();
    applyTheme(state.theme);
    applyWindowTitle();
  });
})();
