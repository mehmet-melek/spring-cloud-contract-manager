<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mikroservis Kontrat Test Stratejileri İnforgrafiği</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Visualization & Content Choices: 
        - Giriş, Neden?, Geleneksel Testler: Goal: Inform. Method: Styled Text (H1, P, UL), Unicode icons (HTML/CSS). Interaction: Read. Justification: Foundational context.
        - Kontrat Testi Yaklaşımları (CDC, PDC, Spec-Driven - İşleyiş, Etki, Avantaj/Dezavantaj, Kullanım Alanları): Goal: Explain, Detail. Method: Card-based layout for each approach, Pros/Cons lists (HTML/CSS with ✅ ❌), Code block for Groovy (PRE tag). Interaction: Read, Scroll. Justification: In-depth, structured explanation. NO SVG/Mermaid.
        - Yaklaşımların Karşılaştırılması (Tablo): Goal: Compare, Organize. Method: HTML Table styled with Tailwind. Interaction: Read, Scroll. Justification: Comprehensive data display.
        - Yaklaşımların Karşılaştırılması (Anahtar Metrikler): Goal: Compare, Visualize. Method: Chart.js Bar Chart (Canvas). Data: Key comparison points (Entegrasyon Güvencesi, Regresyon Koruması etc.) numerically mapped. Interaction: View, Hover for tooltips. Justification: Quick visual summary. Library: Chart.js. NO SVG/Mermaid.
        - Sonuç ve Öneriler: Goal: Inform, Summarize. Method: Styled text sections/cards. Interaction: Read. Justification: Concluding insights.
        - CONFIRMATION: NO SVG graphics used. NO Mermaid JS used.
    -->
    <style>
        body { font-family: 'Inter', sans-serif; scroll-behavior: smooth; }
        .section-title { border-bottom-width: 2px; padding-bottom: 0.5rem; margin-bottom: 1.5rem; }
        .card { background-color: white; border-radius: 0.5rem; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1), 0 2px 4px -1px rgba(0,0,0,0.06); padding: 1.5rem; margin-bottom: 1.5rem; }
        .nav-item.active { color: #06D6A0; font-weight: 600; }
        .chart-container {
            position: relative;
            width: 100%;
            max-width: 700px;
            margin-left: auto;
            margin-right: auto;
            height: 400px; 
            max-height: 450px;
        }
        @media (min-width: 768px) { .chart-container { height: 450px; } }
        .sticky-nav { position: sticky; top: 0; z-index: 50; background-color: rgba(255,255,255,0.95); backdrop-filter: blur(8px); box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .pro-con-list li::before { margin-right: 0.5em; }
        .pro-list li::before { content: '✅'; }
        .con-list li::before { content: '❌'; }
    </style>
</head>
<body class="bg-slate-100 text-slate-800">

    <header class="sticky-nav p-4">
        <div class="container mx-auto flex flex-wrap justify-center md:justify-between items-center">
            <div class="text-xl font-bold text-sky-700 mb-2 md:mb-0">Mikroservis Kontrat Testleri</div>
            <nav>
                <ul class="flex flex-wrap justify-center space-x-3 md:space-x-4 text-sm">
                    <li><a href="#giris" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Giriş</a></li>
                    <li><a href="#neden-kontrat" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Neden?</a></li>
                    <li><a href="#geleneksel-testler" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Geleneksel Testler</a></li>
                    <li><a href="#yaklasimlar" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Yaklaşımlar</a></li>
                    <li><a href="#karsilastirma" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Karşılaştırma</a></li>
                    <li><a href="#sonuc" class="nav-item text-slate-600 hover:text-sky-600 transition-colors">Sonuç</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main class="container mx-auto p-4 md:p-8">
        
        <section id="giris" class="pt-16 -mt-16 card">
            <h2 class="text-3xl md:text-4xl font-bold text-sky-700 mb-4 section-title border-sky-300">1. Giriş: Mikroservislerde Entegrasyonun Önemi ve Zorlukları</h2>
            <p class="text-lg mb-3 text-slate-700">Mikroservis mimarileri, bağımsız geliştirme, ölçeklenebilirlik ve esneklik gibi birçok avantaj sunar. Ancak, servis sayısındaki artışla birlikte servisler arası iletişim ve entegrasyon karmaşıklığı da artar.</p>
            <div class="grid md:grid-cols-2 gap-4">
                <div>
                    <h3 class="text-xl font-semibold text-sky-600 mb-2">Temel Zorluklar</h3>
                    <ul class="list-disc list-inside space-y-1 text-slate-600">
                        <li>Servisler arası bağımlılıkların yönetimi.</li>
                        <li>API uyumluluğunun sürekli sağlanması.</li>
                        <li>Entegrasyon hatalarının üretim öncesinde tespiti.</li>
                        <li>Geleneksel uçtan uca testlerin maliyetli ve yavaş olması.</li>
                    </ul>
                </div>
                <div class="bg-slate-50 p-4 rounded-md">
                    <h3 class="text-xl font-semibold text-sky-600 mb-2">Spring Cloud Contract'e Genel Bakış</h3>
                    <p class="text-slate-600">Consumer-Driven Contract (CDC) ve Provider tarafı testlerini destekleyen bir Java framework'üdür. Kontratların DSL (Groovy, YAML vb.) ile esnek tanımlanmasına olanak tanır. Provider tarafında otomatik test üretimi, Consumer tarafında stub/mock sunucu üretimi sunar.</p>
                </div>
            </div>
        </section>

        <section id="neden-kontrat" class="pt-16 -mt-16 card">
            <h2 class="text-3xl font-bold text-sky-700 mb-4 section-title border-sky-300">Neden Kontrat Testlerine İhtiyaç Duyarız?</h2>
            <div class="grid md:grid-cols-2 gap-6">
                <div class="flex items-start space-x-3 p-3 bg-sky-50 rounded-lg">
                    <span class="text-3xl text-sky-500">🤝</span>
                    <div><h3 class="font-semibold text-lg text-sky-700">Açık Anlaşmalar</h3><p class="text-slate-600">Servisler arasında net bir "anlaşma (kontrat)" tanımlar.</p></div>
                </div>
                <div class="flex items-start space-x-3 p-3 bg-sky-50 rounded-lg">
                    <span class="text-3xl text-sky-500">⏱️</span>
                    <div><h3 class="font-semibold text-lg text-sky-700">Erken Tespit</h3><p class="text-slate-600">Entegrasyon sorunlarını CI/CD'de erken yakalar.</p></div>
                </div>
                <div class="flex items-start space-x-3 p-3 bg-sky-50 rounded-lg">
                    <span class="text-3xl text-sky-500">🚀</span>
                    <div><h3 class="font-semibold text-lg text-sky-700">Bağımsız Geliştirme</h3><p class="text-slate-600">Servislerin bağımsız geliştirilip dağıtılmasını güvence altına alır.</p></div>
                </div>
                <div class="flex items-start space-x-3 p-3 bg-sky-50 rounded-lg">
                    <span class="text-3xl text-sky-500">🎯</span>
                    <div><h3 class="font-semibold text-lg text-sky-700">Beklenti Karşılama</h3><p class="text-slate-600">Sağlayıcının, tüketici beklentilerini karşıladığından emin olur.</p></div>
                </div>
            </div>
        </section>

        <section id="geleneksel-testler" class="pt-16 -mt-16 card">
            <h2 class="text-3xl font-bold text-sky-700 mb-4 section-title border-sky-300">2. Geleneksel Controller Birim (Unit) Testleri</h2>
            <div class="grid md:grid-cols-2 gap-6">
                <div class="bg-slate-50 p-4 rounded-md">
                    <h3 class="text-xl font-semibold text-sky-600 mb-2">Mevcut Yaklaşım</h3>
                    <p class="text-slate-600">Geliştiriciler, yazdıkları controller'ların birim testlerini kendileri (`MockMvc` vb. ile) oluşturur. Controller'ın iç mantığını, istek yönlendirmesini ve temel doğrulamalarını test eder.</p>
                </div>
                <div class="bg-red-50 p-4 rounded-md border border-red-200">
                    <h3 class="text-xl font-semibold text-red-600 mb-2">Sınırlılıkları ve Yetersizlikleri</h3>
                    <ul class="list-disc list-inside space-y-1 text-slate-700">
                        <li>Tüketici beklentilerinden bağımsızdır.</li>
                        <li>Entegrasyon garantisi sunmaz (API değişikliği tüketicileri bozabilir).</li>
                        <li>"Kör noktalar" içerebilir (tüketici kullanım senaryoları eksik kalabilir).</li>
                    </ul>
                </div>
            </div>
        </section>

        <section id="yaklasimlar" class="pt-16 -mt-16">
            <h2 class="text-3xl font-bold text-sky-700 mb-6 section-title border-sky-300 text-center">3. Kontrat Testi Yaklaşımları</h2>
            <div class="grid md:grid-cols-1 lg:grid-cols-3 gap-6">
                
                <article class="card flex flex-col">
                    <h3 class="text-2xl font-semibold text-sky-600 mb-3">Tüketici Odaklı (CDC)</h3>
                    <p class="text-sm text-slate-600 mb-2"><strong>İşleyiş:</strong> Tüketici, sağlayıcıdan beklediği kontratı tanımlar ve yayınlar. Sağlayıcı bu kontratlarla testlerini üretir.</p>
                    <p class="text-sm text-slate-600 mb-3"><strong>Etkisi:</strong> "Testler tüketiciler tarafından yazılır." Sağlayıcı, gereksinimleri doğrudan tüketicilerden doğrulatır.</p>
                    <div class="mt-auto">
                        <h4 class="font-semibold text-md text-green-600 mt-2 mb-1">Avantajları</h4>
                        <ul class="pro-con-list pro-list text-xs space-y-1 text-slate-600"><li>Tüketici Odaklılık</li><li>Erken Geri Bildirim</li><li>Net İletişim</li><li>Bağımsız Dağıtım</li></ul>
                        <h4 class="font-semibold text-md text-red-600 mt-3 mb-1">Dezavantajları</h4>
                        <ul class="pro-con-list con-list text-xs space-y-1 text-slate-600"><li>Sağlayıcıda Yük Potansiyeli</li><li>Sıkı Koordinasyon İhtiyacı</li><li>Hızı Yavaşlatma Potansiyeli</li></ul>
                    </div>
                </article>

                <article class="card flex flex-col">
                    <h3 class="text-2xl font-semibold text-sky-600 mb-3">Sağlayıcı Odaklı (PDC)</h3>
                    <p class="text-sm text-slate-600 mb-2"><strong>İşleyiş:</strong> Sağlayıcı, API kontratlarını kendi tanımlar ve yayınlar. Tüketiciler bu kontratlarla stub/mock üretir.</p>
                    <p class="text-sm text-slate-600 mb-3"><strong>Etkisi:</strong> "Birim testlerinin farklı formatı." Geliştirici bilinçli olarak kontratları ve test senaryolarını oluşturur.</p>
                     <div class="mt-auto">
                        <h4 class="font-semibold text-md text-green-600 mt-2 mb-1">Avantajları</h4>
                        <ul class="pro-con-list pro-list text-xs space-y-1 text-slate-600"><li>Sağlayıcı Kontrolü</li><li>Tüketici Bağımsızlığı (Testte)</li><li>Net API Tanımı</li></ul>
                        <h4 class="font-semibold text-md text-red-600 mt-3 mb-1">Dezavantajları</h4>
                        <ul class="pro-con-list con-list text-xs space-y-1 text-slate-600"><li>Tüketici İhtiyaçlarını Karşılamama Riski</li><li>Tek Taraflı Bakış Açısı</li><li>Kontrat Güncelliği Sorunu</li></ul>
                    </div>
                </article>

                <article class="card flex flex-col">
                    <h3 class="text-2xl font-semibold text-sky-600 mb-3">OpenAPI Odaklı (Spec-Driven)</h3>
                    <p class="text-sm text-slate-600 mb-2"><strong>İşleyiş:</strong> Kod → OpenAPI → Otomatik Kontrat → Otomatik Test.</p>
                    <div class="bg-yellow-50 border border-yellow-200 p-2 rounded-md text-xs my-2">
                        <h4 class="font-semibold text-yellow-700">Kullanım Alanları:</h4>
                        <ul class="list-disc list-inside text-slate-600"><li>Merkezi OpenAPI kullanımı</li><li>Hızlı regresyon kapsamı</li><li>Test kapsamı düşük projeler</li><li>Tamamlayıcı olarak</li></ul>
                    </div>
                     <div class="mt-auto">
                        <h4 class="font-semibold text-md text-green-600 mt-2 mb-1">Avantajları</h4>
                        <ul class="pro-con-list pro-list text-xs space-y-1 text-slate-600"><li>Tek Gerçek Kaynak</li><li>Hızlı Başlangıç/Otomasyon</li><li>Geniş Regresyon Koruması</li><li>API Tasarımına Uyum</li></ul>
                        <h4 class="font-semibold text-md text-red-600 mt-3 mb-1">Dezavantajları</h4>
                        <ul class="pro-con-list con-list text-xs space-y-1 text-slate-600"><li>"Bilinçsiz Test" Eksikliği</li><li>Yanlış Özelliği Doğrulama Riski</li><li>Sınırlı Test Derinliği</li><li>OpenAPI Kalitesine Bağımlılık</li></ul>
                    </div>
                </article>
            </div>
             <div class="card mt-4">
                <h4 class="text-lg font-semibold text-sky-700 mb-2">Örnek CDC Kontratı (Groovy DSL)</h4>
                <pre class="bg-slate-900 text-slate-200 p-3 rounded-md overflow-x-auto text-xs"><code>
import org.springframework.cloud.contract.spec.Contract
Contract.make {
    request {
        method 'GET'
        url '/api/users/123'
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
            id: 123,
            name: "Ahmet Yılmaz",
            email: "ahmet.yilmaz@example.com"
        )
    }
}
                </code></pre>
            </div>
        </section>

        <section id="karsilastirma" class="pt-16 -mt-16 card">
            <h2 class="text-3xl font-bold text-sky-700 mb-4 section-title border-sky-300">4. Yaklaşımların Karşılaştırılması</h2>
            <div class="overflow-x-auto mb-8">
                <table class="min-w-full divide-y divide-slate-300 border border-slate-300">
                    <thead class="bg-slate-200">
                        <tr>
                            <th class="px-3 py-2 text-left text-xs font-semibold text-slate-700 uppercase tracking-wider">Özellik</th>
                            <th class="px-3 py-2 text-left text-xs font-semibold text-slate-700 uppercase tracking-wider">Geleneksel Unit</th>
                            <th class="px-3 py-2 text-left text-xs font-semibold text-slate-700 uppercase tracking-wider">CDC</th>
                            <th class="px-3 py-2 text-left text-xs font-semibold text-slate-700 uppercase tracking-wider">PDC</th>
                            <th class="px-3 py-2 text-left text-xs font-semibold text-slate-700 uppercase tracking-wider">Spec-Driven</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-slate-200 text-xs text-slate-700">
                        <tr><td class="px-3 py-2 font-medium">Ana Odak</td><td class="px-3 py-2">Controller iç mantığı</td><td class="px-3 py-2">Tüketici beklentileri</td><td class="px-3 py-2">Sağlayıcı API tanımı</td><td class="px-3 py-2">API spesifikasyonuna uygunluk</td></tr>
                        <tr><td class="px-3 py-2 font-medium">"Bilinçli Test"</td><td class="px-3 py-2">Yüksek</td><td class="px-3 py-2">Yüksek</td><td class="px-3 py-2">Yüksek</td><td class="px-3 py-2 text-red-600 font-semibold">Düşük</td></tr>
                        <tr><td class="px-3 py-2 font-medium">Entegrasyon Güvencesi</td><td class="px-3 py-2">Düşük</td><td class="px-3 py-2 text-green-600 font-semibold">Yüksek</td><td class="px-3 py-2">Orta</td><td class="px-3 py-2">Orta</td></tr>
                        <tr><td class="px-3 py-2 font-medium">"Yanlış Özelliği Doğrulama" Riski</td><td class="px-3 py-2">Düşük</td><td class="px-3 py-2">Düşük</td><td class="px-3 py-2">Orta</td><td class="px-3 py-2 text-red-600 font-semibold">Yüksek</td></tr>
                        <tr><td class="px-3 py-2 font-medium">Regresyon Koruması</td><td class="px-3 py-2">Kısmi</td><td class="px-3 py-2">İyi</td><td class="px-3 py-2">İyi</td><td class="px-3 py-2 text-green-600 font-semibold">Çok İyi</td></tr>
                    </tbody>
                </table>
            </div>
            <h3 class="text-xl font-semibold text-sky-600 mb-3 text-center">Anahtar Metriklerin Görsel Karşılaştırması</h3>
            <div class="chart-container bg-white p-4 rounded-lg shadow-inner">
                <canvas id="comparisonChart"></canvas>
            </div>
            <p class="text-xs text-slate-500 mt-2 text-center">Grafikteki değerler (1-Düşük, 2-Orta, 3-İyi, 4-Yüksek/Çok İyi) göreceli bir karşılaştırma sunar.</p>
        </section>

        <section id="sonuc" class="pt-16 -mt-16 card">
            <h2 class="text-3xl font-bold text-sky-700 mb-4 section-title border-sky-300">5. Sonuç ve Strateji Önerileri</h2>
            <p class="text-lg mb-4 text-slate-700">Mikroservis entegrasyonunda "herkese uyan tek bir doğru" yaklaşım yoktur. Proje ihtiyaçlarına göre en uygun strateji veya stratejilerin birleşimi seçilmelidir.</p>
            <div class="grid md:grid-cols-2 gap-6">
                <div class="bg-sky-50 p-4 rounded-md border border-sky-200">
                    <h3 class="text-xl font-semibold text-sky-600 mb-2">OpenAPI-Driven Yaklaşım İçin İpuçları</h3>
                    <ul class="list-disc list-inside space-y-1 text-slate-600 text-sm">
                        <li>Mevcut, dokümantasyonu iyi sistemlerde hızlı regresyon ağı için değerlidir.</li>
                        <li>Tek başına yeterli olmayabilir; "yanlış şeyi doğru yapma" riskini azaltmak için bilinçli testler ve/veya CDC ile desteklenmelidir.</li>
                        <li>OpenAPI spesifikasyonlarının detaylı, doğru ve güncel olması başarısı için kritiktir.</li>
                    </ul>
                </div>
                <div class="bg-emerald-50 p-4 rounded-md border border-emerald-200">
                    <h3 class="text-xl font-semibold text-emerald-600 mb-2">Genel Strateji Önerileri</h3>
                     <ul class="list-disc list-inside space-y-1 text-slate-600 text-sm">
                        <li><strong>Karma (Hybrid) Yaklaşımlar Genellikle En İyisidir:</strong>
                            <ul class="list-['▹'] list-inside ml-4 space-y-0.5">
                                <li>Kritik entegrasyonlar için CDC.</li>
                                <li>Genel API uygunluğu ve regresyon için OpenAPI-Driven.</li>
                                <li>Sağlayıcı garantileri ve stub için PDC unsurları.</li>
                            </ul>
                        </li>
                        <li>Kontrat testleri, teknik araçların ötesinde **iletişim ve işbirliği** araçlarıdır.</li>
                    </ul>
                </div>
            </div>
        </section>
    </main>

    <footer class="text-center py-8 bg-slate-800 text-slate-400 text-sm">
        <p>&copy; 2024 Mikroservis Kontrat Test Stratejileri İnforgrafiği. Tüm hakları saklıdır.</p>
    </footer>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const navItems = document.querySelectorAll('.nav-item');
            const sections = document.querySelectorAll('section[id]');

            const observerOptions = {
                root: null,
                rootMargin: "0px",
                threshold: 0.4 
            };

            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    const id = entry.target.getAttribute('id');
                    const navLink = document.querySelector(`.nav-item[href="#${id}"]`);
                    if (entry.isIntersecting) {
                        navItems.forEach(link => link.classList.remove('active'));
                        if (navLink) {
                            navLink.classList.add('active');
                        }
                    }
                });
            }, observerOptions);

            sections.forEach(section => {
                observer.observe(section);
            });
            
            navItems.forEach(item => {
                item.addEventListener('click', function(e) {
                    navItems.forEach(link => link.classList.remove('active'));
                    this.classList.add('active');
                });
            });


            function processLabel(label, maxLength = 16) {
                if (label.length <= maxLength) {
                    return label;
                }
                const words = label.split(' ');
                const lines = [];
                let currentLine = '';
                for (const word of words) {
                    if ((currentLine + word).length > maxLength && currentLine.length > 0) {
                        lines.push(currentLine.trim());
                        currentLine = word + ' ';
                    } else {
                        currentLine += word + ' ';
                    }
                }
                if (currentLine.trim().length > 0) {
                    lines.push(currentLine.trim());
                }
                return lines;
            }
            
            const chartLabels = ['Geleneksel Unit', 'Tüketici Odaklı (CDC)', 'Sağlayıcı Odaklı (PDC)', 'OpenAPI Odaklı (Spec-Driven)'].map(label => processLabel(label));


            const ctx = document.getElementById('comparisonChart')?.getContext('2d');
            if (ctx) {
                const dataMap = { 'Düşük': 1, 'Kısmi': 1, 'Orta': 2, 'İyi': 3, 'Yüksek': 4, 'Çok İyi': 4 };
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: chartLabels,
                        datasets: [
                            {
                                label: 'Entegrasyon Güvencesi',
                                data: [dataMap['Düşük'], dataMap['Yüksek'], dataMap['Orta'], dataMap['Orta']],
                                backgroundColor: '#06D6A0', 
                            },
                            {
                                label: 'Regresyon Koruması',
                                data: [dataMap['Kısmi'], dataMap['İyi'], dataMap['İyi'], dataMap['Çok İyi']],
                                backgroundColor: '#FFD166',
                            },
                            {
                                label: '"Bilinçli Test" Seviyesi',
                                data: [dataMap['Yüksek'], dataMap['Yüksek'], dataMap['Yüksek'], dataMap['Düşük']],
                                backgroundColor: '#118AB2', 
                            },
                             {
                                label: '"Yanlış Özelliği Doğrulama" Riski',
                                data: [dataMap['Düşük'], dataMap['Düşük'], dataMap['Orta'], dataMap['Yüksek']],
                                backgroundColor: '#FF6B6B',
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    stepSize: 1,
                                    callback: function(value) {
                                        const tickLabels = ['', 'Düşük/Kısmi', 'Orta', 'İyi', 'Yüksek/Çok İyi'];
                                        return tickLabels[value] || '';
                                    }
                                },
                                grid: { color: 'rgba(200, 200, 200, 0.2)' }
                            },
                            x: {
                                grid: { display: false }
                            }
                        },
                        plugins: {
                            legend: { position: 'bottom', labels: { boxWidth: 12, padding: 15, font: {size: 10} } },
                            tooltip: {
                                mode: 'index',
                                intersect: false,
                                callbacks: {
                                    title: function(tooltipItems) {
                                        const item = tooltipItems[0];
                                        let label = item.chart.data.labels[item.dataIndex];
                                        return Array.isArray(label) ? label.join(' ') : label;
                                    },
                                    label: function(context) {
                                        let label = context.dataset.label || '';
                                        if (label) { label += ': '; }
                                        const value = context.parsed.y;
                                        const tickLabels = ['', 'Düşük/Kısmi', 'Orta', 'İyi', 'Yüksek/Çok İyi'];
                                        label += tickLabels[value] || '';
                                        return label;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    </script>
</body>
</html>
