<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.BufferedReader, java.io.InputStreamReader, java.net.HttpURLConnection, java.net.URL" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date, java.util.TimeZone" %>
<%!
    // 네이버 금융 API를 서버단에서 백엔드 통신으로 호출하는 메서드
    public String fetchApiResponse(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }
%>
<%
    // Server-side AJAX Proxy 처리 (실시간 시세 & 과거 일별 종가 요청 대응)
    String action = request.getParameter("action");
    if ("getRealtime".equals(action)) {
        response.setContentType("application/json; charset=UTF-8");
        String code = request.getParameter("code");
        String json = fetchApiResponse("https://polling.finance.naver.com/api/realtime/domestic/stock/" + code);
        if (json == null) json = "{}";
        out.print(json);
        out.flush();
        return;
    } else if ("getDaily".equals(action)) {
        response.setContentType("application/json; charset=UTF-8");
        String code = request.getParameter("code");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String json = fetchApiResponse("https://api.finance.naver.com/siseJson.naver?symbol=" + code + "&requestType=1&startTime=" + start + "&endTime=" + end + "&timeframe=day");
        if (json == null) json = "[]";
        out.print(json);
        out.flush();
        return;
    }

    // 서버 기준 KST 현재 날짜 구하기
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
    String serverToday = sdf.format(new Date());
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>삼성전자 / 삼성전자우 주가 현황</title>
<style>
  .viz-root {
    color-scheme: light;
    --surface-1:      #fcfcfb;
    --page-plane:     #f9f9f7;
    --text-primary:   #0b0b0b;
    --text-secondary: #52514e;
    --muted:          #898781;
    --grid:           #e1e0d9;
    --baseline:       #c3c2b7;
    --border:         rgba(11,11,11,0.10);
    --up:             #e34948;
    --down:           #2a78d6;
    --flat:           #898781;
    --live-dot:       #0ca30c;
    --warn:           #fab219;
  }
  @media (prefers-color-scheme: dark) {
    :root:where(:not([data-theme="light"])) .viz-root {
      color-scheme: dark;
      --surface-1:      #1a1a19;
      --page-plane:     #0d0d0d;
      --text-primary:   #ffffff;
      --text-secondary: #c3c2b7;
      --muted:          #898781;
      --grid:           #2c2c2a;
      --baseline:       #383835;
      --border:         rgba(255,255,255,0.10);
      --up:             #e66767;
      --down:           #3987e5;
      --flat:           #898781;
      --live-dot:       #0ca30c;
    }
  }

  * { box-sizing: border-box; }
  html, body {
    margin: 0; padding: 0;
    background: var(--page-plane);
    font-family: system-ui, -apple-system, "Segoe UI", sans-serif;
    color: var(--text-primary);
  }
  .viz-root {
    background: var(--page-plane);
    min-height: 100vh;
    padding: 28px 20px 60px;
  }
  .wrap { max-width: 980px; margin: 0 auto; }

  header.page-head {
    display: flex; flex-wrap: wrap; align-items: baseline;
    justify-content: space-between; gap: 12px;
    margin-bottom: 20px;
  }
  header.page-head h1 {
    font-size: 22px; font-weight: 700; margin: 0;
  }
  header.page-head .sub {
    font-size: 13px; color: var(--text-secondary); margin-top: 4px;
  }
  .clock-box {
    text-align: right; font-size: 13px; color: var(--text-secondary);
  }
  .clock-box .time {
    font-size: 18px; font-weight: 700; color: var(--text-primary);
    font-variant-numeric: tabular-nums;
  }
  .market-badge {
    display: inline-flex; align-items: center; gap: 6px;
    padding: 2px 10px; border-radius: 999px; font-size: 12px; font-weight: 600;
    margin-top: 4px; border: 1px solid var(--border);
  }
  .market-badge.open { color: var(--live-dot); }
  .market-badge.closed { color: var(--muted); }
  .dot { width: 7px; height: 7px; border-radius: 50%; background: currentColor; }
  .dot.pulse { animation: pulse 1.4s ease-in-out infinite; }
  @keyframes pulse {
    0%, 100% { opacity: 1; transform: scale(1); }
    50% { opacity: 0.35; transform: scale(0.8); }
  }

  .stat-tiles {
    display: grid; grid-template-columns: 1fr auto 1fr; align-items: center; gap: 14px;
    margin-bottom: 22px;
  }
  @media (max-width: 640px) { .stat-tiles { grid-template-columns: 1fr; } }
  .tile {
    background: var(--surface-1);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 18px 20px;
  }
  .tile .tile-head {
    display: flex; justify-content: space-between; align-items: center;
    margin-bottom: 8px;
  }
  .tile .name { font-size: 14px; font-weight: 600; color: var(--text-secondary); }
  .tile .live-tag {
    display: inline-flex; align-items: center; gap: 5px;
    font-size: 11px; font-weight: 700; color: var(--live-dot);
  }
  .tile .price {
    font-size: 32px; font-weight: 700; font-variant-numeric: tabular-nums;
    line-height: 1.2;
  }
  .tile .delta {
    margin-top: 4px; font-size: 14px; font-weight: 600;
    font-variant-numeric: tabular-nums;
  }
  .tile .delta.up { color: var(--up); }
  .tile .delta.down { color: var(--down); }
  .tile .delta.flat { color: var(--flat); }
  .tile .meta {
    margin-top: 12px; padding-top: 12px; border-top: 1px solid var(--grid);
    display: flex; justify-content: space-between; font-size: 12px; color: var(--muted);
  }
  .tile .meta b { color: var(--text-secondary); font-weight: 600; }

  .qty-diff-badge {
    display: flex; flex-direction: column; align-items: center; justify-content: center;
    gap: 2px;
    background: var(--surface-1);
    border: 1px solid var(--border);
    border-radius: 999px;
    padding: 10px 16px;
    white-space: nowrap;
    text-align: center;
    cursor: default;
  }
  .qty-diff-badge .vs-label {
    font-size: 10px; font-weight: 700; letter-spacing: 0.06em; color: var(--muted);
  }
  .qty-diff-badge .qty-diff-value {
    font-size: 13px; font-weight: 700; color: var(--text-primary);
  }
  @media (max-width: 640px) {
    .qty-diff-badge { border-radius: 12px; padding: 8px 16px; }
  }

  .section-label {
    font-size: 13px; font-weight: 600; color: var(--text-secondary);
    margin: 4px 0 10px;
  }
  .div-tiles {
    display: grid; grid-template-columns: 1fr auto 1fr; align-items: center; gap: 14px;
    margin-bottom: 22px;
  }
  @media (max-width: 640px) { .div-tiles { grid-template-columns: 1fr; } }
  .div-tile {
    background: var(--surface-1);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 16px 20px;
  }
  .div-tile .div-tile-head {
    font-size: 14px; font-weight: 600; color: var(--text-secondary);
    margin-bottom: 10px;
  }
  .div-row {
    display: flex; justify-content: space-between; align-items: baseline;
    padding: 5px 0; font-size: 13px; color: var(--muted);
    font-variant-numeric: tabular-nums;
  }
  .div-row span:last-child { color: var(--text-secondary); font-weight: 600; }
  .div-row.total {
    margin-top: 6px; padding-top: 10px; border-top: 1px solid var(--grid);
    font-size: 14px; color: var(--text-primary);
  }
  .div-row.total span:last-child { color: var(--text-primary); font-weight: 700; font-size: 15px; }

  .conn-status {
    font-size: 12px; color: var(--muted); margin: 4px 0 18px; text-align: right;
  }
  .conn-status.error { color: var(--warn); }
  .conn-status button {
    margin-left: 8px; font-size: 12px; padding: 3px 10px; border-radius: 6px;
    border: 1px solid var(--border); background: var(--surface-1); color: var(--text-primary);
    cursor: pointer;
  }
  .conn-status button:hover { border-color: var(--baseline); }

  .table-wrap {
    background: var(--surface-1);
    border: 1px solid var(--border);
    border-radius: 12px;
    overflow: hidden;
  }
  table { width: 100%; border-collapse: collapse; font-size: 13px; }
  thead th {
    text-align: right; padding: 10px 14px; font-weight: 600;
    color: var(--muted); font-size: 11px; letter-spacing: 0.02em;
    border-bottom: 1px solid var(--grid); white-space: nowrap;
  }
  thead th:first-child, thead th:nth-child(2) { text-align: left; }
  tbody td {
    text-align: right; padding: 9px 14px; border-bottom: 1px solid var(--grid);
    font-variant-numeric: tabular-nums; white-space: nowrap;
  }
  tbody td:first-child, tbody td:nth-child(2) { text-align: left; }
  tbody tr:last-child td { border-bottom: none; }
  tbody tr.today { background: color-mix(in srgb, var(--live-dot) 6%, transparent); }
  tbody tr.holiday td, tbody tr.nodata td { color: var(--muted); }
  .diff-cell { font-weight: 700; }
  .diff-cell.pos { color: var(--up); }
  .diff-cell.neg { color: var(--down); }
  .row-status {
    display: inline-flex; align-items: center; gap: 5px; font-size: 11px;
    padding: 2px 8px; border-radius: 999px; border: 1px solid var(--border);
    color: var(--muted);
  }
  .row-status.live { color: var(--live-dot); border-color: color-mix(in srgb, var(--live-dot) 40%, transparent); }

  footer.note {
    margin-top: 16px; font-size: 11px; color: var(--muted); line-height: 1.6;
  }
</style>
</head>
<body>
<div class="viz-root">
  <div class="wrap">

    <header class="page-head">
      <div>
        <h1>삼성전자 · 삼성전자우 주가 현황</h1>
        <div class="sub">2026-08-11 ~ 오늘 · 날짜 범위를 매번 자동 계산 · 지난 날짜는 종가, 오늘은 1초 단위 실시간</div>
      </div>
      <div class="clock-box">
        <div class="time" id="clock">--:--:--</div>
        <div id="marketBadge" class="market-badge closed"><span class="dot"></span><span>확인 중</span></div>
      </div>
    </header>

    <section class="stat-tiles">
      <div class="tile">
        <div class="tile-head">
          <span class="name">삼성전자 (005930)</span>
          <span class="live-tag" id="tagSamsung"><span class="dot pulse"></span>LIVE</span>
        </div>
        <div class="price" id="priceSamsung">-</div>
        <div class="delta flat" id="deltaSamsung">-</div>
        <div class="meta">
          <span>보유 576주</span>
          <span>평가금액 <b id="amtSamsung">-</b></span>
        </div>
      </div>
      <div class="qty-diff-badge" id="qtyDiffBadge"></div>
      <div class="tile">
        <div class="tile-head">
          <span class="name">삼성전자우 (005935)</span>
          <span class="live-tag" id="tagPreferred"><span class="dot pulse"></span>LIVE</span>
        </div>
        <div class="price" id="pricePreferred">-</div>
        <div class="delta flat" id="deltaPreferred">-</div>
        <div class="meta">
          <span>보유 810주</span>
          <span>평가금액 <b id="amtPreferred">-</b></span>
        </div>
      </div>
    </section>

    <div class="section-label" id="divSectionLabel">배당금 정보 · 주당 4,500원 · 원천징수 15.4%</div>
    <section class="div-tiles" id="divTiles">
      <div class="div-tile">
        <div class="div-tile-head">삼성전자 배당금</div>
        <div class="div-row"><span>배당금(주당)</span><span id="divPerShareS">4,500원</span></div>
        <div class="div-row"><span>보유수량</span><span>576주</span></div>
        <div class="div-row"><span>배당금 총액</span><span id="divGrossS">-</span></div>
        <div class="div-row"><span>원천징수 15.4%</span><span id="divTaxS">-</span></div>
        <div class="div-row total"><span>세후 배당금</span><span id="divNetS">-</span></div>
      </div>
      <div class="qty-diff-badge" id="divDiffBadge"></div>
      <div class="div-tile">
        <div class="div-tile-head">삼성전자우 배당금</div>
        <div class="div-row"><span>배당금(주당)</span><span id="divPerShareP">4,500원</span></div>
        <div class="div-row"><span>보유수량</span><span>810주</span></div>
        <div class="div-row"><span>배당금 총액</span><span id="divGrossP">-</span></div>
        <div class="div-row"><span>원천징수 15.4%</span><span id="divTaxP">-</span></div>
        <div class="div-row total"><span>세후 배당금</span><span id="divNetP">-</span></div>
      </div>
    </section>

    <div class="conn-status" id="historySource">일별 종가 조회 중…</div>
    <div class="conn-status" id="connStatus">실시간 연결 대기 중…</div>

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>날짜</th>
            <th>상태</th>
            <th>삼성전자 현재가</th>
            <th>삼성전자우 현재가</th>
            <th>삼성전자 금액</th>
            <th>삼성전자우 금액</th>
            <th>차액</th>
          </tr>
        </thead>
        <tbody id="historyBody"></tbody>
      </table>
    </div>

    <footer class="note">
      주식수: 삼성전자 576주 · 삼성전자우 810주 (고정) · 금액 = 현재가 × 주식수 · 차액 = 삼성전자 금액 − 삼성전자우 금액.<br>
      Tomcat JSP 프록시 서버를 통해 네이버 금융 API 연동을 수행하여 브라우저의 CORS 제한 없이 실시간 시세 및 과거 종가 데이터를 동적으로 수집합니다.
    </footer>

  </div>
</div>

<script>
(async function () {
  const QTY = { samsung: 576, preferred: 810 };
  const START_DATE = '2026-08-11';
  const NAVER_CODE = { samsung: '005930', preferred: '005935' };
  const SERVER_TODAY = '<%= serverToday %>';

  const FALLBACK_HISTORY = [
    { date: '2026-08-11', samsung: 239500, preferred: 180200 },
    { date: '2026-08-12', samsung: 255500, preferred: 186100 },
    { date: '2026-08-13', samsung: 268000, preferred: 187800 },
    { date: '2026-08-14', samsung: 274500, preferred: 195600 },
    { date: '2026-08-18', samsung: 268500, preferred: 188300 },
    { date: '2026-08-19', samsung: 253500, preferred: 176000 }
  ];

  const fmt = n => n == null ? '-' : n.toLocaleString('ko-KR');
  const won = n => n == null ? '-' : fmt(n) + '원';

  function renderQtyDiff() {
    const el = document.getElementById('qtyDiffBadge');
    if (!el) return;
    const diff = Math.abs(QTY.samsung - QTY.preferred);
    el.title = '삼성전자 ' + fmt(QTY.samsung) + '주 · 삼성전자우 ' + fmt(QTY.preferred) + '주';
    if (diff === 0) {
      el.innerHTML = '<span class="vs-label">VS</span><span class="qty-diff-value">보유수량 동일</span>';
      return;
    }
    const more = QTY.samsung > QTY.preferred ? '삼성전자' : '삼성전자우';
    el.innerHTML = '<span class="vs-label">VS</span><span class="qty-diff-value">' + more + ' +' + fmt(diff) + '주</span>';
  }
  renderQtyDiff();

  // ---------- 배당금(세후) 계산 ----------
  const DIVIDEND_PER_SHARE = 4500;
  const WITHHOLDING_RATE = 0.154;

  function renderDividend() {
    const rows = [
      { qty: QTY.samsung, gross: 'divGrossS', tax: 'divTaxS', net: 'divNetS' },
      { qty: QTY.preferred, gross: 'divGrossP', tax: 'divTaxP', net: 'divNetP' }
    ];
    const net = {};
    rows.forEach((r, i) => {
      const gross = DIVIDEND_PER_SHARE * r.qty;
      const tax = Math.round(gross * WITHHOLDING_RATE);
      const n = gross - tax;
      document.getElementById(r.gross).textContent = won(gross);
      document.getElementById(r.tax).textContent = '-' + won(tax);
      document.getElementById(r.net).textContent = won(n);
      net[i === 0 ? 's' : 'p'] = n;
    });

    const badge = document.getElementById('divDiffBadge');
    if (!badge) return;
    badge.title = '삼성전자 세후 배당금 ' + won(net.s) + ' · 삼성전자우 세후 배당금 ' + won(net.p);
    const diff = Math.abs(net.s - net.p);
    if (diff === 0) {
      badge.innerHTML = '<span class="vs-label">VS</span><span class="qty-diff-value">세후 배당금 동일</span>';
      return;
    }
    const more = net.s > net.p ? '삼성전자' : '삼성전자우';
    badge.innerHTML = '<span class="vs-label">VS</span><span class="qty-diff-value">' + more + ' +' + won(diff) + '</span>';
  }
  renderDividend();

  function weekdayKo(dateStr) {
    const [y, m, d] = dateStr.split('-').map(Number);
    const day = new Date(Date.UTC(y, m - 1, d)).getUTCDay();
    return ['일','월','화','수','목','금','토'][day];
  }

  function kstNow() {
    const now = new Date();
    return new Date(now.toLocaleString('en-US', { timeZone: 'Asia/Seoul' }));
  }

  function toDateStr(d) {
    const y = d.getFullYear();
    const m = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return y + '-' + m + '-' + day;
  }

  function isMarketOpen(kst) {
    const day = kst.getDay();
    if (day === 0 || day === 6) return false;
    const mins = kst.getHours() * 60 + kst.getMinutes();
    return mins >= 9 * 60 && mins <= 15 * 60 + 30;
  }

  // ---------- JSP 백엔드 프록시를 통해 API 데이터 호출 ----------
  async function fetchRealtimeFromJsp(code) {
    const res = await fetch('?action=getRealtime&code=' + code, { cache: 'no-store' });
    if (!res.ok) throw new Error('JSP HTTP ' + res.status);
    const json = await res.json();
    const d = json && json.result && json.result.areas && json.result.areas[0] && json.result.areas[0].datas && json.result.areas[0].datas[0];
    if (!d) throw new Error('Data Empty');
    return { price: Number(d.nv), prevClose: Number(d.pcv) };
  }

  async function fetchDailyFromJsp(code, startCompact, endCompact) {
    const res = await fetch('?action=getDaily&code=' + code + '&start=' + startCompact + '&end=' + endCompact, { cache: 'no-store' });
    if (!res.ok) throw new Error('JSP HTTP ' + res.status);
    const text = (await res.text()).trim();
    if (!text || text === '[]') throw new Error('Daily Empty');
    const rows = JSON.parse(text.replace(/'/g, '"'));
    const map = {};
    for (let i = 1; i < rows.length; i++) {
      const r = rows[i];
      if (!r || r.length < 5 || !r[0]) continue;
      const raw = String(r[0]).trim();
      if (!/^\d{8}$/.test(raw)) continue;
      const dateStr = raw.slice(0,4) + '-' + raw.slice(4,6) + '-' + raw.slice(6,8);
      map[dateStr] = Number(r[4]);
    }
    return map;
  }

  async function buildHistory() {
    const todayStr = SERVER_TODAY;
    const startCompact = START_DATE.replace(/-/g, '');
    const endCompact = todayStr.replace(/-/g, '');
    try {
      const [sMap, pMap] = await Promise.all([
        fetchDailyFromJsp(NAVER_CODE.samsung, startCompact, endCompact),
        fetchDailyFromJsp(NAVER_CODE.preferred, startCompact, endCompact)
      ]);
      const allDates = Array.from(new Set([...Object.keys(sMap), ...Object.keys(pMap)])).sort();
      if (allDates.length === 0) throw new Error('빈 응답');
      if (!allDates.includes(todayStr)) allDates.push(todayStr);

      const history = allDates.map(d => ({
        date: d,
        status: d === todayStr ? 'live' : 'closed',
        samsung: sMap[d] ?? null,
        preferred: pMap[d] ?? null
      }));
      return { history, source: 'dynamic', todayStr };
    } catch (e) {
      console.warn('일별 종가 자동 조회 실패, 폴백 데이터 사용', e);
      const history = FALLBACK_HISTORY.map(r => ({ ...r, status: 'closed' }));
      if (history.length === 0 || history[history.length - 1].date !== todayStr) {
        const last = history[history.length - 1] || { samsung: null, preferred: null };
        history.push({ date: todayStr, status: 'live', samsung: last.samsung, preferred: last.preferred });
      } else {
        history[history.length - 1].status = 'live';
      }
      return { history, source: 'fallback', todayStr };
    }
  }

  const { history: HISTORY, source: historySourceType, todayStr: TODAY_STR } = await buildHistory();

  const historySourceEl = document.getElementById('historySource');
  if (historySourceType === 'dynamic') {
    historySourceEl.textContent = '일별 종가: 자동 조회 (' + HISTORY[0].date + ' ~ ' + TODAY_STR + ')';
  } else {
    historySourceEl.className = 'conn-status error';
    historySourceEl.textContent = '일별 종가 자동 조회 실패 · 대체 저장값 표시 중 (' + HISTORY[0].date + ' ~ ' + TODAY_STR + ')';
  }

  const lastIdx = HISTORY.length - 1;
  const prevEntry = lastIdx > 0 ? HISTORY[lastIdx - 1] : null;
  const PREV_CLOSE = {
    samsung: prevEntry?.samsung ?? HISTORY[lastIdx].samsung ?? 0,
    preferred: prevEntry?.preferred ?? HISTORY[lastIdx].preferred ?? 0
  };

  // ---------- 테이블 렌더 (날짜 내림차순: 최신 날짜가 위로) ----------
  const tbody = document.getElementById('historyBody');
  const rowRefs = {};

  HISTORY.slice().reverse().forEach(row => {
    const tr = document.createElement('tr');
    tr.className = row.status === 'live' ? 'today' : '';

    const statusLabel = row.status === 'live'
      ? '<span class="row-status live"><span class="dot pulse" style="width:6px;height:6px;"></span>실시간</span>'
      : '<span class="row-status">종가</span>';

    const amtS = row.samsung != null ? row.samsung * QTY.samsung : null;
    const amtP = row.preferred != null ? row.preferred * QTY.preferred : null;
    const diff = (amtS != null && amtP != null) ? amtS - amtP : null;

    tr.innerHTML = '<td>' + row.date + ' (' + weekdayKo(row.date) + ')</td>' +
      '<td>' + statusLabel + '</td>' +
      '<td data-cell="price-s">' + fmt(row.samsung) + '</td>' +
      '<td data-cell="price-p">' + fmt(row.preferred) + '</td>' +
      '<td data-cell="amt-s">' + won(amtS) + '</td>' +
      '<td data-cell="amt-p">' + won(amtP) + '</td>' +
      '<td class="diff-cell ' + (diff == null ? '' : (diff >= 0 ? 'pos' : 'neg')) + '" data-cell="diff">' + (diff == null ? '-' : won(diff)) + '</td>';

    tbody.appendChild(tr);
    if (row.status === 'live') rowRefs.today = tr;
  });

  // ---------- 시계 / 장중 배지 ----------
  const clockEl = document.getElementById('clock');
  const badgeEl = document.getElementById('marketBadge');

  function tickClock() {
    const kst = kstNow();
    const hh = String(kst.getHours()).padStart(2, '0');
    const mm = String(kst.getMinutes()).padStart(2, '0');
    const ss = String(kst.getSeconds()).padStart(2, '0');
    clockEl.textContent = hh + ':' + mm + ':' + ss + ' (KST)';
    const open = isMarketOpen(kst);
    badgeEl.className = 'market-badge ' + (open ? 'open' : 'closed');
    badgeEl.innerHTML = '<span class="dot ' + (open ? 'pulse' : '') + '"></span><span>' + (open ? '장중' : '장마감') + '</span>';

    const nowStr = toDateStr(kst);
    if (nowStr !== TODAY_STR) {
      location.reload();
    }
  }
  tickClock();
  setInterval(tickClock, 1000);

  // ---------- 실시간 시세 폴링 ----------
  const els = {
    samsung: {
      price: document.getElementById('priceSamsung'),
      delta: document.getElementById('deltaSamsung'),
      amt: document.getElementById('amtSamsung')
    },
    preferred: {
      price: document.getElementById('pricePreferred'),
      delta: document.getElementById('deltaPreferred'),
      amt: document.getElementById('amtPreferred')
    }
  };
  const connEl = document.getElementById('connStatus');

  let lastGood = {
    samsung: { price: HISTORY[lastIdx].samsung ?? PREV_CLOSE.samsung, prevClose: PREV_CLOSE.samsung },
    preferred: { price: HISTORY[lastIdx].preferred ?? PREV_CLOSE.preferred, prevClose: PREV_CLOSE.preferred }
  };
  let failStreak = 0;

  function renderPrice(key, price, prevClose) {
    const amount = price * QTY[key];
    const change = price - prevClose;
    const rate = prevClose ? (change / prevClose * 100) : 0;
    const dir = change > 0 ? 'up' : (change < 0 ? 'down' : 'flat');
    const arrow = dir === 'up' ? '▲' : (dir === 'down' ? '▼' : '-');

    els[key].price.textContent = fmt(price);
    els[key].delta.className = 'delta ' + dir;
    els[key].delta.textContent = arrow + ' ' + fmt(Math.abs(change)) + ' (' + (rate >= 0 ? '+' : '') + rate.toFixed(2) + '%)';
    els[key].amt.textContent = won(amount);

    if (rowRefs.today) {
      const cellTarget = key === 'samsung' ? 's' : 'p';
      const cellPrice = rowRefs.today.querySelector('[data-cell="price-' + cellTarget + '"]');
      const cellAmt = rowRefs.today.querySelector('[data-cell="amt-' + cellTarget + '"]');
      if (cellPrice) cellPrice.textContent = fmt(price);
      if (cellAmt) cellAmt.textContent = won(amount);
    }
  }

  function renderDiffRow() {
    if (!rowRefs.today) return;
    const sPrice = lastGood.samsung.price, pPrice = lastGood.preferred.price;
    const diff = (sPrice * QTY.samsung) - (pPrice * QTY.preferred);
    const cell = rowRefs.today.querySelector('[data-cell="diff"]');
    if (cell) {
      cell.textContent = won(diff);
      cell.className = 'diff-cell ' + (diff >= 0 ? 'pos' : 'neg');
    }
  }

  async function pollOnce() {
    try {
      const [s, p] = await Promise.all([
        fetchRealtimeFromJsp(NAVER_CODE.samsung),
        fetchRealtimeFromJsp(NAVER_CODE.preferred)
      ]);
      lastGood.samsung = s;
      lastGood.preferred = p;
      renderPrice('samsung', s.price, s.prevClose);
      renderPrice('preferred', p.price, p.prevClose);
      renderDiffRow();
      failStreak = 0;
      connEl.className = 'conn-status';
      const now = kstNow();
      connEl.innerHTML = '실시간 연결됨 · 마지막 갱신 ' + String(now.getHours()).padStart(2,'0') + ':' + String(now.getMinutes()).padStart(2,'0') + ':' + String(now.getSeconds()).padStart(2,'0');
    } catch (e) {
      failStreak++;
      renderPrice('samsung', lastGood.samsung.price, lastGood.samsung.prevClose);
      renderPrice('preferred', lastGood.preferred.price, lastGood.preferred.prevClose);
      renderDiffRow();
      connEl.className = 'conn-status error';
      connEl.innerHTML = '실시간 데이터 수집 중… <button id="retryBtn">새로고침</button>';
      const btn = document.getElementById('retryBtn');
      if (btn) btn.onclick = pollOnce;
    }
  }

  renderPrice('samsung', lastGood.samsung.price, lastGood.samsung.prevClose);
  renderPrice('preferred', lastGood.preferred.price, lastGood.preferred.prevClose);
  renderDiffRow();

  pollOnce();
  setInterval(() => {
    if (failStreak >= 3) {
      if (Date.now() % 5000 < 1000) pollOnce();
    } else {
      pollOnce();
    }
  }, 1000);

})();
</script>
</body>
</html>