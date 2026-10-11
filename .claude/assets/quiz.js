/* Shared interactive components for lessons.
 *
 * 1. Multiple choice
 *    <div class="quiz" data-id="l1-q1">
 *      <p class="q">Question?</p>
 *      <div class="opts">
 *        <button class="opt" data-correct>Right answer</button>
 *        <button class="opt">Wrong answer</button>
 *      </div>
 *      <div class="why">Explanation shown after answering.</div>
 *    </div>
 *    Options are shuffled on load, so source order never leaks the answer.
 *
 * 2. Ordering
 *    <div class="order" data-id="l1-o1">
 *      <p class="q">Put these in order</p>
 *      <ol class="answer" hidden><li>first</li><li>second</li>…</ol>
 *    </div>
 *    Items are shuffled into clickable buttons; learner clicks them in order, then "Check".
 *
 * 3. Score bar: add <div class="score" data-lesson="0001"></div>; it counts first-try correct answers
 *    and remembers completion per browser (localStorage, best-effort).
 */
(function () {
  'use strict';

  function store(key, value) {
    try {
      if (value === undefined) return JSON.parse(localStorage.getItem('maku-teach:' + key));
      localStorage.setItem('maku-teach:' + key, JSON.stringify(value));
    } catch (e) { return null; }
  }

  function shuffle(arr) {
    for (let i = arr.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [arr[i], arr[j]] = [arr[j], arr[i]];
    }
    return arr;
  }

  const score = { total: 0, answered: 0, firstTry: 0 };
  let scoreEl = null;

  function renderScore() {
    if (!scoreEl) return;
    const { total, answered, firstTry } = score;
    scoreEl.textContent = answered < total
      ? `Practice: ${answered}/${total} answered · ${firstTry} right first time`
      : `Done — ${firstTry}/${total} right first time. ${firstTry === total ? 'Excellent.' : 'Revisit the ones you missed in a day or two.'}`;
    if (answered === total && total > 0) {
      const lesson = scoreEl.dataset.lesson;
      if (lesson) store('lesson:' + lesson, { done: true, firstTry, total, at: new Date().toISOString() });
    }
  }

  function initQuiz(el) {
    score.total++;
    const opts = el.querySelector('.opts');
    const buttons = shuffle(Array.from(opts.querySelectorAll('button.opt')));
    buttons.forEach(b => opts.appendChild(b));

    const verdict = document.createElement('p');
    verdict.className = 'verdict';
    const why = el.querySelector('.why');
    if (why) why.prepend(verdict);

    let attempts = 0;
    buttons.forEach(btn => {
      btn.type = 'button';
      btn.addEventListener('click', () => {
        if (el.classList.contains('done')) return;
        attempts++;
        const correct = btn.hasAttribute('data-correct');
        if (correct) {
          btn.classList.add('right');
          el.classList.add('done');
          buttons.forEach(b => (b.disabled = true));
          verdict.className = 'verdict ok';
          verdict.textContent = attempts === 1 ? '✓ Correct.' : `✓ Correct (after ${attempts} tries).`;
          score.answered++;
          if (attempts === 1) score.firstTry++;
          renderScore();
        } else {
          btn.classList.add('wrong');
          btn.disabled = true;
        }
      });
    });
  }

  function initOrder(el) {
    score.total++;
    const answer = Array.from(el.querySelectorAll('ol.answer li')).map(li => li.innerHTML);
    const pool = document.createElement('div'); pool.className = 'pool';
    const picked = document.createElement('div'); picked.className = 'picked';
    const controls = document.createElement('div'); controls.className = 'controls';
    const result = document.createElement('p'); result.className = 'result';
    const check = document.createElement('button'); check.textContent = 'Check order'; check.type = 'button';
    const reset = document.createElement('button'); reset.textContent = 'Reset'; reset.type = 'button';
    controls.append(check, reset);
    el.append(pool, picked, controls, result);
    const whyBlock = el.querySelector('.why');
    if (whyBlock) el.appendChild(whyBlock);

    let attempts = 0, finished = false;

    function build() {
      pool.innerHTML = ''; picked.innerHTML = ''; result.textContent = '';
      shuffle(answer.map((html, i) => ({ html, i }))).forEach(({ html, i }) => {
        const b = document.createElement('button');
        b.type = 'button'; b.className = 'item'; b.innerHTML = html; b.dataset.i = i;
        b.addEventListener('click', () => {
          if (finished) return;
          (b.parentElement === pool ? picked : pool).appendChild(b);
          b.classList.remove('right', 'wrong');
        });
        pool.appendChild(b);
      });
    }
    build();

    check.addEventListener('click', () => {
      if (finished) return;
      const items = Array.from(picked.children);
      if (items.length !== answer.length) { result.textContent = 'Place every step first.'; return; }
      attempts++;
      let ok = true;
      items.forEach((b, pos) => {
        const good = Number(b.dataset.i) === pos;
        b.classList.toggle('right', good); b.classList.toggle('wrong', !good);
        if (!good) ok = false;
      });
      if (ok) {
        finished = true;
        result.style.color = 'var(--accent-3)';
        result.textContent = attempts === 1 ? '✓ Perfect order.' : `✓ Correct (after ${attempts} tries).`;
        score.answered++; if (attempts === 1) score.firstTry++;
        renderScore();
        const why = el.querySelector('.why'); if (why) why.hidden = false;
      } else {
        result.style.color = 'var(--bad)';
        result.textContent = 'Not quite — red steps are in the wrong position. Click them to move back, then retry.';
      }
    });
    reset.addEventListener('click', () => { if (!finished) build(); });
  }

  function initIndex() {
    document.querySelectorAll('[data-lesson-link]').forEach(li => {
      const rec = store('lesson:' + li.dataset.lessonLink);
      if (rec && rec.done) {
        const mark = document.createElement('span');
        mark.className = 'done-mark';
        mark.textContent = `✓ ${rec.firstTry}/${rec.total}`;
        li.querySelector('a').after(mark);
      }
    });
  }

  document.addEventListener('DOMContentLoaded', () => {
    scoreEl = document.querySelector('.score');
    document.querySelectorAll('.quiz').forEach(initQuiz);
    document.querySelectorAll('.order').forEach(initOrder);
    document.querySelectorAll('.order .why').forEach(w => (w.hidden = true));
    renderScore();
    initIndex();
  });
})();
