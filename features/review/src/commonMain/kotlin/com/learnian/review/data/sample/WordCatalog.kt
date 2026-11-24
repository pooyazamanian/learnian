package com.learnian.review.data.sample

import com.learnian.review.domain.model.ContextSentence
import com.learnian.review.domain.model.FsrsIntervals
import com.learnian.review.domain.model.ReviewProgressInfo
import com.learnian.review.domain.model.WordCollocation
import com.learnian.review.domain.model.WordDerivative
import com.learnian.review.domain.model.WordEtymology
import com.learnian.review.domain.model.WordExample
import com.learnian.review.domain.model.WordMnemonic
import com.learnian.review.domain.model.WordReviewData

/**
 * Offline-first fallback catalog for the review feature.
 *
 * The remote API is always queried first; when it is unreachable or the payload
 * is missing, these bundled entries keep the flow usable. Every entry has a
 * different shape (list lengths, optional blocks) so the UI is exercised against
 * variable server payloads.
 */
class WordCatalog {

    private val words: Map<String, WordReviewData> = listOf(
        abandonWord(),
        resilientWord(),
        meticulousWord(),
    ).associateBy { it.wordId }

    fun find(wordId: String): WordReviewData? = words[wordId]

    fun all(): List<WordReviewData> = words.values.toList()

    private fun abandonWord(): WordReviewData = WordReviewData(
        wordId = "word_abandon_1",
        word = "Abandon",
        phonetic = "/əˈbændən/",
        level = "B2/C1",
        lesson = "درس ۲",
        partsOfSpeech = listOf("Verb", "Noun"),
        primaryPartOfSpeechFa = "فعل",
        primaryPartOfSpeechEn = "Verb",
        persianMeanings = listOf("رها کردن", "ترک کردن", "واگذار کردن", "دست کشیدن از"),
        persianExplanation = "دست برداشتن عمدی و همیشگی از یک شیء، مکان، آرمان، مسئولیت یا یک شخص.",
        simpleEnglishDefinition = "To leave a place, thing, or person, usually forever, or to stop doing or planning something.",
        contextSentence = ContextSentence(
            englishText = "They had to abandon the project due to lack of funds.",
            highlightedWord = "abandon",
            persianText = "«آن‌ها مجبور شدند به دلیل کمبود بودجه پروژه را رها کنند.»",
        ),
        synonyms = listOf("Leave", "Desert", "Quit", "Relinquish", "Forsake"),
        antonyms = listOf("Maintain", "Retain", "Keep"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «آبان + دَن»",
            story = "فکر کن یک ناخدا وسط آب و باد تصمیم می‌گیرد کشتی پر از طلا را کامل رها کند!",
            hookFa = "«اَ بَند و بست زندگی قبلی رو رها کرد و رفت!»",
            highlightedHook = "اَ بَند",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه فرانسوی قدیم",
            rootTerm = "à bandon",
            rootMeaning = "به معنای «تحت اختیار گذاردن»",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Abandoned",
                    partOfSpeech = "صفت",
                    persianMeaning = "متروکه، رهاشده",
                    examplePhrase = "an abandoned house",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Abandonment",
                    partOfSpeech = "اسم",
                    persianMeaning = "واگذاری، رهاشدگی",
                    examplePhrase = "fear of abandonment",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "abandon ship", persianMeaning = "ترک کشتی در خطر", iconKey = "ship"),
            WordCollocation(englishPhrase = "abandon hope", persianMeaning = "قطع کامل امید", iconKey = "sad"),
            WordCollocation(englishPhrase = "abandon a plan", persianMeaning = "صرف‌نظر از برنامه", iconKey = "cancel"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "The sailors were forced to abandon the sinking ship in heavy weather.",
                highlightedWord = "abandon",
                persianText = "ملوانان به دلیل وضعیت وخیم آب و هوا مجبور شدند کشتی در حال غرق‌شدن را ترک کنند.",
            ),
            WordExample(
                englishText = "They decided to abandon their research project due to lack of funds.",
                highlightedWord = "abandon",
                persianText = "آن‌ها به دلیل کمبود بودجه تصمیم گرفتند از پروژه تحقیقاتی خود دست بکشند.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 3,
            totalCount = 10,
            streakDays = 7,
            coachTip = "«بریم سراغ یک کلمه کلیدی که در آزمون‌ها پرتکرار است...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۱۰ دقیقه",
            hardText = "۱ روز",
            goodText = "۴ روز",
            easyText = "۸ روز",
        ),
        isBookmarked = true,
    )

    private fun resilientWord(): WordReviewData = WordReviewData(
        wordId = "word_resilient_2",
        word = "Resilient",
        phonetic = "/rɪˈzɪliənt/",
        level = "B2",
        lesson = "درس ۵",
        partsOfSpeech = listOf("Adjective"),
        primaryPartOfSpeechFa = "صفت",
        primaryPartOfSpeechEn = "Adjective",
        persianMeanings = listOf("تاب‌آور", "انعطاف‌پذیر", "سریع در بازگشت به حالت اولیه"),
        persianExplanation = "توانایی بازگشت سریع به وضعیت عادی پس از سختی، ضربه یا تغییر.",
        simpleEnglishDefinition = "Able to recover quickly from difficult conditions or change.",
        contextSentence = ContextSentence(
            englishText = "Children are often more resilient than adults expect.",
            highlightedWord = "resilient",
            persianText = "«کودکان اغلب تاب‌آورتر از آن چیزی هستند که بزرگ‌ترها انتظار دارند.»",
        ),
        synonyms = listOf("Tough", "Durable", "Adaptable", "Buoyant"),
        antonyms = listOf("Fragile", "Brittle", "Vulnerable"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «ری + زمین»",
            story = "توپ لاستیکی را روی زمین فشار می‌دهی؛ فوراً برمی‌گردد بالا. همین «برگشتن» یعنی resilient.",
            hookFa = "«ریزالینت = ری + زل + زدن به زمین و بلند شدن دوباره»",
            highlightedHook = "ری + زل",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه لاتین",
            rootTerm = "resilire",
            rootMeaning = "به معنای «به عقب پریدن»",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Resilience",
                    partOfSpeech = "اسم",
                    persianMeaning = "تاب‌آوری",
                    examplePhrase = "emotional resilience",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Resiliently",
                    partOfSpeech = "قید",
                    persianMeaning = "با تاب‌آوری",
                    examplePhrase = "recover resiliently",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "resilient economy", persianMeaning = "اقتصاد تاب‌آور", iconKey = "link"),
            WordCollocation(englishPhrase = "highly resilient", persianMeaning = "بسیار تاب‌آور", iconKey = "target"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "The local community proved remarkably resilient after the flood.",
                highlightedWord = "resilient",
                persianText = "جامعه محلی پس از سیل به‌شکل چشمگیری تاب‌آور از خود نشان داد.",
            ),
            WordExample(
                englishText = "A resilient mindset helps you learn from failure instead of fearing it.",
                highlightedWord = "resilient",
                persianText = "ذهنیت تاب‌آور کمک می‌کند از شکست یاد بگیری، نه اینکه از آن بترسی.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 7,
            totalCount = 12,
            streakDays = 5,
            coachTip = "«امروز روی کلماتی تمرکز می‌کنیم که در مکالمه‌های روزمره زیاد می‌شنوی...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۵ دقیقه",
            hardText = "۲ روز",
            goodText = "۶ روز",
            easyText = "۱۲ روز",
        ),
        isBookmarked = true,
    )

    private fun meticulousWord(): WordReviewData = WordReviewData(
        wordId = "word_meticulous_3",
        word = "Meticulous",
        phonetic = "/məˈtɪkjələs/",
        level = "C1",
        lesson = "درس ۹",
        partsOfSpeech = listOf("Adjective"),
        primaryPartOfSpeechFa = "صفت",
        primaryPartOfSpeechEn = "Adjective",
        persianMeanings = listOf("بسیار دقیق", "موشکافانه", "وسواس‌گونه در جزئیات"),
        persianExplanation = "دقت و توجه شدید و وسواس‌گونه به کوچک‌ترین جزئیات یک کار.",
        simpleEnglishDefinition = "Showing great attention to detail; very careful and precise.",
        contextSentence = ContextSentence(
            englishText = "She kept meticulous notes during every experiment.",
            highlightedWord = "meticulous",
            persianText = "«او در طول هر آزمایش یادداشت‌های بسیار دقیقی نگه می‌داشت.»",
        ),
        synonyms = listOf("Precise", "Thorough", "Scrupulous"),
        antonyms = listOf("Careless", "Sloppy", "Hasty"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «مِتی + کیلو»",
            story = "یک ساعت‌ساز را تصور کن که با «متر» و «کیلو» هر قطعه را تا میلی‌متر اندازه می‌گیرد؛ این‌قدر دقیق!",
            hookFa = "«متیکولوس = متر و کیلو در دست، دقیق‌ترین آدم شهر»",
            highlightedHook = "متر و کیلو",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه لاتین",
            rootTerm = "meticulosus",
            rootMeaning = "در اصل «ترسناک»، سپس «با ترس و دقت»",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Meticulously",
                    partOfSpeech = "قید",
                    persianMeaning = "با دقت بسیار",
                    examplePhrase = "meticulously planned",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Meticulousness",
                    partOfSpeech = "اسم",
                    persianMeaning = "دقت وسواس‌گونه",
                    examplePhrase = "the meticulousness of a surgeon",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "meticulous attention", persianMeaning = "توجه بی‌نقص", iconKey = "target"),
            WordCollocation(englishPhrase = "meticulous planning", persianMeaning = "برنامه‌ریزی موشکافانه", iconKey = "link"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "The restoration required meticulous attention to the original materials.",
                highlightedWord = "meticulous",
                persianText = "مرمت نیازمند توجه بسیار دقیق به مواد اولیه بود.",
            ),
            WordExample(
                englishText = "His meticulous preparation made the presentation look effortless.",
                highlightedWord = "meticulous",
                persianText = "آماده‌سازی موشکافانه‌اش باعث شد ارائه بی‌نقص به نظر برسد.",
            ),
            WordExample(
                englishText = "A meticulous editor catches details that everyone else overlooks.",
                highlightedWord = "meticulous",
                persianText = "ویراستار دقیق جزئیاتی را می‌بیند که بقیه از آن غافل می‌شوند.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 10,
            totalCount = 12,
            streakDays = 21,
            coachTip = "«نزدیک پایان مجموعه‌ایم؛ این واژه‌های سطح بالا را محکم نگه دار...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۱۰ دقیقه",
            hardText = "۳ روز",
            goodText = "۹ روز",
            easyText = "۱۸ روز",
        ),
    )

    private fun benevolentWord(): WordReviewData = WordReviewData(
        wordId = "word_benevolent_4",
        word = "Benevolent",
        phonetic = "/bəˈnevələnt/",
        level = "B2",
        lesson = "درس ۷",
        partsOfSpeech = listOf("Adjective"),
        primaryPartOfSpeechFa = "صفت",
        primaryPartOfSpeechEn = "Adjective",
        persianMeanings = listOf("خیرخواه", "نیکوکار", "بخشنده"),
        persianExplanation = "داشتن نیت خیر و تمایل خالصانه برای کمک به دیگران.",
        simpleEnglishDefinition = "Kind and generous; wishing to do good to other people.",
        contextSentence = ContextSentence(
            englishText = "A benevolent smile lit up her face.",
            highlightedWord = "benevolent",
            persianText = "«لبخندی خیرخواهانه چهره‌اش را روشن کرد.»",
        ),
        synonyms = listOf("Kind", "Generous", "Compassionate", "Charitable"),
        antonyms = listOf("Cruel", "Selfish", "Malevolent"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «بِن + وُلِنت»",
            story = "یک «بِن» (پسر) داوطلب را تصور کن که همیشه اولین نفر برای کمک به دیگران است.",
            hookFa = "«بِن وُلِنت = پسرِ داوطلبِ خیرخواه»",
            highlightedHook = "بِن وُلِنت",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه لاتین",
            rootTerm = "bene + velle",
            rootMeaning = "«خوب» + «خواستن» یعنی خوب خواستن",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Benevolence",
                    partOfSpeech = "اسم",
                    persianMeaning = "خیرخواهی، نیکوکاری",
                    examplePhrase = "an act of benevolence",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Benevolently",
                    partOfSpeech = "قید",
                    persianMeaning = "از روی خیرخواهی",
                    examplePhrase = "she smiled benevolently",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "benevolent smile", persianMeaning = "لبخند خیرخواهانه", iconKey = "sad"),
            WordCollocation(englishPhrase = "benevolent fund", persianMeaning = "صندوق کمک خیریه", iconKey = "link"),
            WordCollocation(englishPhrase = "benevolent dictator", persianMeaning = "دیکتاتور خیرخواه", iconKey = "target"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "The benevolent donor funded the entire scholarship program.",
                highlightedWord = "benevolent",
                persianText = "اهداکننده خیرخواه هزینه کل برنامه بورسیه را تأمین کرد.",
            ),
            WordExample(
                englishText = "A benevolent teacher stayed after class to help every student.",
                highlightedWord = "benevolent",
                persianText = "معلمی خیرخواه بعد از کلاس می‌ماند تا به همه دانش‌آموزان کمک کند.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 6,
            totalCount = 12,
            streakDays = 9,
            coachTip = "«این واژه در متن‌های ادبی زیاد می‌آید؛ یک مثال برایش بساز...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۵ دقیقه",
            hardText = "۱ روز",
            goodText = "۵ روز",
            easyText = "۱۰ روز",
        ),
        isBookmarked = true,
    )

    private fun ephemeralWord(): WordReviewData = WordReviewData(
        wordId = "word_ephemeral_5",
        word = "Ephemeral",
        phonetic = "/ɪˈfemərəl/",
        level = "C1",
        lesson = "درس ۱۱",
        partsOfSpeech = listOf("Adjective"),
        primaryPartOfSpeechFa = "صفت",
        primaryPartOfSpeechEn = "Adjective",
        persianMeanings = listOf("زودگذر", "ناپایدار", "کم‌دوام"),
        persianExplanation = "چیزی که تنها برای مدتی بسیار کوتاه وجود دارد و به سرعت محو می‌شود.",
        simpleEnglishDefinition = "Lasting for a very short time; transitory.",
        contextSentence = ContextSentence(
            englishText = "Fame in the digital era can be remarkably ephemeral.",
            highlightedWord = "ephemeral",
            persianText = "«شهرت در عصر دیجیتال می‌تواند به‌طور چشمگیری زودگذر باشد.»",
        ),
        synonyms = listOf("Fleeting", "Transient", "Brief", "Momentary"),
        antonyms = listOf("Permanent", "Enduring", "Lasting", "Eternal"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «اِف + مِرال»",
            story = "یک حباب صابون را تصور کن: یک لحظه می‌درخشد، می‌چرخد و قبل از اینکه خوب نگاهش کنی ترکیده است.",
            hookFa = "«اِفِمرال = افکتِ گذرا در فیلم»",
            highlightedHook = "افکتِ گذرا",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه یونانی",
            rootTerm = "epi + hemera",
            rootMeaning = "«روی» + «روز» یعنی فقط برای یک روز",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Ephemera",
                    partOfSpeech = "اسم",
                    persianMeaning = "چیزهای زودگذر، آثار کم‌دوام",
                    examplePhrase = "a collection of Victorian ephemera",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Ephemerally",
                    partOfSpeech = "قید",
                    persianMeaning = "به‌صورت زودگذر",
                    examplePhrase = "exist ephemerally",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "ephemeral nature", persianMeaning = "ماهیت زودگذر", iconKey = "cancel"),
            WordCollocation(englishPhrase = "ephemeral trend", persianMeaning = "ترند گذرا", iconKey = "link"),
            WordCollocation(englishPhrase = "ephemeral pleasure", persianMeaning = "لذت زودگذر", iconKey = "sad"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "The beauty of cherry blossoms is ephemeral, lasting only a few weeks.",
                highlightedWord = "ephemeral",
                persianText = "زیبایی شکوفه‌های گیلاس زودگذر است و فقط چند هفته دوام دارد.",
            ),
            WordExample(
                englishText = "Most online fame is ephemeral and disappears within weeks.",
                highlightedWord = "ephemeral",
                persianText = "بیشتر شهرت‌های اینترنتی زودگذرند و در عرض چند هفته محو می‌شوند.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 8,
            totalCount = 12,
            streakDays = 14,
            coachTip = "«کلمات هم‌خانواده را با هم مرور کن تا در حافظه دوام بیشتری پیدا کنند...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۵ دقیقه",
            hardText = "۱ روز",
            goodText = "۳ روز",
            easyText = "۶ روز",
        ),
        isBookmarked = true,
    )

    private fun pragmaticWord(): WordReviewData = WordReviewData(
        wordId = "word_pragmatic_6",
        word = "Pragmatic",
        phonetic = "/præɡˈmætɪk/",
        level = "B2/C1",
        lesson = "درس ۱۳",
        partsOfSpeech = listOf("Adjective"),
        primaryPartOfSpeechFa = "صفت",
        primaryPartOfSpeechEn = "Adjective",
        persianMeanings = listOf("عمل‌گرا", "واقع‌بینانه", "کارکردی"),
        persianExplanation = "برخورد با امور بر پایه شرایط واقعی و نتایج عملی، به جای ایده‌آل‌های نظری.",
        simpleEnglishDefinition = "Dealing with things sensibly and realistically, based on practical considerations.",
        contextSentence = ContextSentence(
            englishText = "We need a pragmatic approach to solve this budget issue.",
            highlightedWord = "pragmatic",
            persianText = "«ما به یک رویکرد عمل‌گرایانه برای حل این مشکل بودجه نیاز داریم.»",
        ),
        synonyms = listOf("Practical", "Realistic", "Sensible", "Businesslike"),
        antonyms = listOf("Idealistic", "Impractical", "Theoretical", "Dogmatic"),
        mnemonic = WordMnemonic(
            title = "تصویرسازی ذهنی: «پراگ + ماتیک»",
            story = "در شهر «پراگ» یک مهندس ریاضی («ماتیک») زندگی می‌کند که فقط چیزهایی می‌سازد که واقعاً کار کنند.",
            hookFa = "«پراگ + ماتیک = حسابِ عمل‌گرا در شهر پراگ»",
            highlightedHook = "پراگ + ماتیک",
            codingTag = "AI کدینگ",
        ),
        etymology = WordEtymology(
            rootOrigin = "ریشه یونانی",
            rootTerm = "pragma",
            rootMeaning = "به معنای «کار، عمل» یعنی مربوط به عمل",
            derivatives = listOf(
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Pragmatism",
                    partOfSpeech = "اسم",
                    persianMeaning = "عمل‌گرایی",
                    examplePhrase = "a spirit of pragmatism",
                ),
                WordDerivative(
                    title = "مشتقات رایج",
                    englishTerm = "Pragmatically",
                    partOfSpeech = "قید",
                    persianMeaning = "به‌صورت عمل‌گرایانه",
                    examplePhrase = "pragmatically speaking",
                ),
            ),
        ),
        collocations = listOf(
            WordCollocation(englishPhrase = "pragmatic approach", persianMeaning = "رویکرد عمل‌گرا", iconKey = "target"),
            WordCollocation(englishPhrase = "pragmatic solution", persianMeaning = "راه‌حل عملی", iconKey = "link"),
            WordCollocation(englishPhrase = "pragmatic decision", persianMeaning = "تصمیم واقع‌بینانه", iconKey = "ship"),
        ),
        realExamples = listOf(
            WordExample(
                englishText = "Her pragmatic response calmed the whole team.",
                highlightedWord = "pragmatic",
                persianText = "واکنش عمل‌گرایانه او کل تیم را آرام کرد.",
            ),
            WordExample(
                englishText = "The government took a pragmatic view of the energy crisis.",
                highlightedWord = "pragmatic",
                persianText = "دولت نگاه عمل‌گرایانه‌ای به بحران انرژی داشت.",
            ),
        ),
        reviewProgress = ReviewProgressInfo(
            currentIndex = 5,
            totalCount = 12,
            streakDays = 11,
            coachTip = "«این واژه در مکالمه‌های کاری پرکاربرد است؛ با یک جمله واقعی تمرینش کن...»",
        ),
        fsrsIntervals = FsrsIntervals(
            againText = "کمتر از ۱۰ دقیقه",
            hardText = "۲ روز",
            goodText = "۶ روز",
            easyText = "۱۲ روز",
        ),
    )
}
