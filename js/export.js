/* ============================================
   CodeSnap — PNG Export & Copy to Clipboard
   ============================================ */

(function () {
  'use strict';

  const exportBtn = document.getElementById('export-png');
  const copyBtn = document.getElementById('copy-clipboard');
  const codeCard = document.getElementById('code-card');

  // ---- Toast Notification ----
  function showToast(message, type) {
    // Remove existing toast
    const existing = document.querySelector('.toast');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = 'toast ' + (type || '');
    toast.innerHTML = (type === 'success'
      ? '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#22c55e" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"/></svg>'
      : '') + message;
    document.body.appendChild(toast);

    // Trigger animation
    requestAnimationFrame(function () {
      requestAnimationFrame(function () {
        toast.classList.add('show');
      });
    });

    setTimeout(function () {
      toast.classList.remove('show');
      setTimeout(function () { toast.remove(); }, 300);
    }, 3000);
  }

  // ---- Capture the code card as canvas ----
  function captureCard() {
    return html2canvas(codeCard, {
      backgroundColor: null,
      scale: 2,
      useCORS: true,
      logging: false,
      removeContainer: true,
    });
  }

  // ---- Export as PNG ----
  exportBtn.addEventListener('click', function () {
    exportBtn.disabled = true;
    exportBtn.textContent = 'Exporting...';

    captureCard().then(function (canvas) {
      const link = document.createElement('a');
      link.download = 'codesnap.png';
      link.href = canvas.toDataURL('image/png');
      link.click();
      showToast('Image saved!', 'success');
    }).catch(function (err) {
      console.error('Export failed:', err);
      showToast('Export failed. Please try again.');
    }).finally(function () {
      exportBtn.disabled = false;
      exportBtn.innerHTML = '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg> Export PNG';
    });
  });

  // ---- Copy to Clipboard ----
  copyBtn.addEventListener('click', function () {
    copyBtn.disabled = true;
    copyBtn.textContent = 'Copying...';

    captureCard().then(function (canvas) {
      return new Promise(function (resolve, reject) {
        canvas.toBlob(function (blob) {
          if (!blob) {
            reject(new Error('Failed to create blob'));
            return;
          }

          // Use Clipboard API
          if (navigator.clipboard && navigator.clipboard.write) {
            navigator.clipboard.write([
              new ClipboardItem({ 'image/png': blob })
            ]).then(resolve).catch(reject);
          } else {
            reject(new Error('Clipboard API not supported'));
          }
        }, 'image/png');
      });
    }).then(function () {
      showToast('Copied to clipboard!', 'success');
    }).catch(function (err) {
      console.error('Copy failed:', err);
      showToast('Copy failed. Try using Export instead.');
    }).finally(function () {
      copyBtn.disabled = false;
      copyBtn.innerHTML = '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="9" y="9" width="13" height="13" rx="2" ry="2"/><path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"/></svg> Copy Image';
    });
  });
})();
