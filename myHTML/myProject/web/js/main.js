document.querySelectorAll(".toc__item").forEach((link) => {
    link.addEventListener("click", (e) => {
        e.preventDefault();
        const target = document.querySelector(link.getAttribute("href"));
        target?.scrollIntoView({ behavior: "smooth" });
    });
});

//https://www.codewithrandom.com/2024/04/20/20-css-water-effects/ water-effect (긁어온 코드)
(() => {
    const scrollIndicator = document.querySelector(".scroll-indicator");
    const sparkleFx = document.getElementById("sparkleFx");
    if (!scrollIndicator || !sparkleFx) return;

    const BUBBLE_COUNT = 50;
    // 좌우로 흔드는 사인파 값 (11개 지점, 0~100%를 10%씩 분할)
    const WOBBLE_STEPS = [0.95, 0.31, -0.59, -1, -0.59, 0.31, 0.95];
    let triggered = false;

    function spawnSparkles() {
        const vh = window.innerHeight;

        for (let i = 0; i < BUBBLE_COUNT; i++) {
            const bubble = document.createElement("span");
            bubble.className = "bubble";

            // 두 난수를 평균내면 중앙(50)에 몰리고 가장자리로 갈수록 옅어지는 삼각분포가 됨
            const left = ((Math.random() + Math.random()) / 2) * 100;
            const distFromCenter = Math.abs(left - 50) / 50; // 0(중앙) ~ 1(가장자리)

            const size = 4 + Math.random() * 22;
            const wobble = 10 + Math.random() * 30;
            const fallPx = ((95 - distFromCenter * 55) + Math.random() * 20) / 100 * vh; // 중앙일수록 더 깊게
            const durationMs = 800 + Math.random() * 800;
            const delayMs = Math.random() * 350;

            bubble.style.left = `${left}vw`;
            bubble.style.width = `${size}px`;
            bubble.style.height = `${size}px`;

            sparkleFx.appendChild(bubble);

            // 낙하 + 흔들림: px 단위로 직접 계산해서 처음부터 끝까지 속도가 완전히 일정
            const fallFrames = WOBBLE_STEPS.map((s, idx) => {
                // 1. 기본 진행률 (0.0 ~ 1.0)
                const t = idx / 10;

                // 2. Ease-out Cubic 공식 적용: 처음에 빠르고 끝에서 부드럽게 느려짐
                const easedT = 1 - Math.pow(1 - t, 3);

                return {
                    transform: `translate(${(s * wobble).toFixed(2)}px, ${(fallPx * easedT).toFixed(2)}px)`,
                    offset: t,
                };
            });
            bubble.animate(fallFrames, {
                duration: durationMs,
                delay: delayMs,
                easing: "linear",
                fill: "forwards",
            });

            // 페이드 인/아웃: 낙하 애니메이션과 완전히 독립적으로 재생
            bubble.animate(
                [
                    { opacity: 0, offset: 0 },
                    { opacity: 1, offset: 0.12 },
                    { opacity: 1, offset: 0.88 },
                    { opacity: 0, offset: 1 },
                ],
                {
                    duration: durationMs,
                    delay: delayMs,
                    easing: "linear",
                    fill: "forwards",
                }
            );
        }

        window.setTimeout(() => {
            sparkleFx.innerHTML = "";
        }, 2200);
    }

    function checkSparkleTrigger() {
        if (triggered) return;
        const triggerLine = window.innerHeight * 0.4;
        if (scrollIndicator.getBoundingClientRect().top <= triggerLine) {
            triggered = true;
            spawnSparkles();
            window.removeEventListener("scroll", checkSparkleTrigger);
        }
    }

    window.addEventListener("scroll", checkSparkleTrigger, { passive: true });
})();

(() => {
    const cards = document.querySelectorAll(".tech-card");
    if (!cards.length) return;

    const cardIO = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    entry.target.classList.add("in");
                    cardIO.unobserve(entry.target);
                }
            });
        },
        { threshold: 0.35 }
    );

    cards.forEach((card) => cardIO.observe(card));
})();

(() => {
    const revealEls = document.querySelectorAll(".reveal");
    if (!revealEls.length) return;

    const revealIO = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) entry.target.classList.add("in");
            });
        },
        { threshold: 0.15 }
    );

    revealEls.forEach((el) => revealIO.observe(el));
})();

(() => {
    const growthSec = document.querySelector(".section--projects-summary");
    const growthCenter = document.getElementById("growthCenter");
    const gFill = document.getElementById("gThreadFill");
    const gItems = document.querySelectorAll(".timeline__item");
    if (!growthSec || !growthCenter) return;

    function growthScroll() {
        const r = growthSec.getBoundingClientRect();
        const vh = window.innerHeight;
        const prog = Math.min(1, Math.max(0, (vh * 0.5 - r.top) / (r.height * 0.55)));
        growthCenter.style.opacity = String(1 - prog);
        growthCenter.style.transform = `translateY(${prog * -30}px) scale(${1 - prog * 0.08})`;
    }

    window.addEventListener("scroll", growthScroll, { passive: true });
    growthScroll();

    if (gFill && gItems.length) {
        const gIO = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) entry.target.classList.add("in");
                });
                let last = -1;
                gItems.forEach((n, i) => {
                    if (n.classList.contains("in")) last = i;
                });
                gFill.style.height = `${((last + 1) / gItems.length) * 100}%`;
            },
            { threshold: 0.4 }
        );
        gItems.forEach((n) => gIO.observe(n));
    }
})();

(() => {
    const phoneFinal = document.getElementById("phoneFinal");
    if (!phoneFinal) return;

    const pIO = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    phoneFinal.classList.add("in");
                    pIO.unobserve(entry.target);
                }
            });
        },
        { threshold: 0.3 }
    );

    pIO.observe(phoneFinal);
})();

