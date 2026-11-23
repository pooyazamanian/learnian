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
}
