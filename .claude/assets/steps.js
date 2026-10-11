/* Hands-on step lists for real-world (terminal) practice.
 *
 *   <ol class="steps" data-id="k8s-l1-steps">
 *     <li>
 *       <p>What to do and why.</p>
 *       <pre><code>kubectl get nodes</code></pre>
 *       <details class="expect"><summary>Expected output</summary><pre>NAME …</pre></details>
 *     </li>
 *   </ol>
 *
 * Adds: a "done" checkbox per step (remembered per browser), a Copy button on every command block,
 * and a progress line. Expected output stays collapsed so the learner predicts before peeking.
 */
(function () {
  'use strict';

  function store(key, value) {
    try {
      if (value === undefined) return JSON.parse(localStorage.getItem('maku-teach:' + key));
      localStorage.setItem('maku-teach:' + key, JSON.stringify(value));
    } catch (e) { return null; }
  }

  function addCopy(pre) {
    const btn = document.createElement('button');
    btn.type = 'button'; btn.className = 'copy'; btn.textContent = 'Copy';
    btn.addEventListener('click', () => {
      const text = pre.querySelector('code') ? pre.querySelector('code').innerText : pre.innerText;
      const done = () => { btn.textContent = 'Copied'; setTimeout(() => (btn.textContent = 'Copy'), 1200); };
      if (navigator.clipboard) navigator.clipboard.writeText(text).then(done, () => {});
    });
    pre.classList.add('has-copy');
    pre.appendChild(btn);
  }

  function initSteps(list) {
    const id = list.dataset.id || 'steps';
    const saved = store('steps:' + id) || {};
    const items = Array.from(list.children).filter(el => el.tagName === 'LI');
    const progress = document.createElement('p');
    progress.className = 'steps-progress';
    list.after(progress);

    function render() {
      const n = items.filter((_, i) => saved[i]).length;
      progress.textContent = n === items.length ? `All ${n} steps done ✓` : `${n} / ${items.length} steps done`;
    }

    items.forEach((li, i) => {
      const box = document.createElement('input');
      box.type = 'checkbox'; box.className = 'step-done'; box.checked = !!saved[i];
      box.setAttribute('aria-label', 'Mark step ' + (i + 1) + ' done');
      li.classList.toggle('done', box.checked);
      box.addEventListener('change', () => {
        saved[i] = box.checked; store('steps:' + id, saved);
        li.classList.toggle('done', box.checked); render();
      });
      li.prepend(box);
      li.querySelectorAll(':scope > pre').forEach(addCopy);
    });
    render();
  }

  document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('ol.steps').forEach(initSteps);
  });
})();
