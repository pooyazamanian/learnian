package com.learnian.quiz.data.sample

import com.learnian.quiz.domain.model.QuizOption
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import com.learnian.quiz.domain.model.QuizQuestionType
import com.learnian.quiz.domain.model.QuizSession
import com.learnian.quiz.domain.model.QuizWord

/**
 * Offline quiz round used while the backend is not deployed yet.
 *
 * It carries one sample of every supported format so all five screen bodies stay
 * reachable without a server. The shape is identical to a real payload, so switching
 * to the API only means removing this fallback from the repository.
 */
class QuizCatalog {

    fun session(): QuizSession = QuizSession(
        id = "quiz_daily_001",
        streakDays = 12,
        accuracyPercent = 92,
        currentIndex = 0,
        totalCount = questions.size,
        questions = questions,
    )

    private val questions: List<QuizQuestion> = listOf(
        benevolentMeaningQuestion(),
        benevolentReverseQuestion(),
        listeningQuestion(),
        spellingQuestion(),
        sentenceBlankQuestion(),
    )

    /** Format 1: English word -> Persian meaning. */
    private fun benevolentMeaningQuestion() = QuizQuestion(
        id = "q_en_fa_benevolent",
        type = QuizQuestionType.EN_TO_FA,
        word = QuizWord(
            term = "Benevolent",
            phonetic = "/bəˈnev.əl.ənt/",
            level = "B2",
            partOfSpeech = "صفت (Adjective)",
            meaning = "خیرخواه، مهربان و نیکوکار",
            definition = "فرد یا نهادی که با نیت خیر به دیگران کمک می‌کند، بخشنده است و مایل به انجام کارهای خیر برای دیگران می‌باشد.",
            contextSentence = "A benevolent donor funded the children's hospital.",
            contextTranslation = "یک نیکوکار خیرخواه هزینه ساخت بیمارستان کودکان را پرداخت کرد.",
            mnemonic = "دست‌های بخشنده‌ای که هدیه‌ای نورانی به نیازمند می‌دهند؛ تصویر «خوبی» را در ذهن ماندگار می‌کند.",
            etymology = "پیشوند لاتین Bene- به معنای «خوب و نیکو» است (مثل benefit و benefactor).",
            aiHint = "متضاد کلیدی این واژه Malevolent (بدخواه) است.",
        ),
        payload = QuizPayload.MeaningChoice(
            options = listOf(
                QuizOption(id = "o1", text = "خیرخواه و نیکوکار"),
                QuizOption(id = "o2", text = "بدخواه و کینه‌توز"),
                QuizOption(id = "o3", text = "مبهم و دوپهلو"),
                QuizOption(id = "o4", text = "سرسخت و لجوج"),
            ),
            correctOptionId = "o1",
        ),
    )

    /** Format 2: Persian meaning -> English word. */
    private fun benevolentReverseQuestion() = QuizQuestion(
        id = "q_fa_en_benevolent",
        type = QuizQuestionType.FA_TO_EN,
        word = QuizWord(
            term = "Benevolent",
            phonetic = "/bəˈnev.əl.ənt/",
            level = "B2",
            partOfSpeech = "صفت (Adjective)",
            meaning = "خیرخواه، مهربان و نیکوکار",
            definition = "کسی که با نیت خیر به نیازمندان کمک می‌کند، بخشنده است و برای دیگران کار خیر انجام می‌دهد.",
            contextSentence = "A ______ donor funded the children's hospital.",
            contextTranslation = "یک نیکوکار خیرخواه هزینه ساخت بیمارستان کودکان را پرداخت کرد.",
            mnemonic = "تصویر ذهنی: دست‌هایی که بی‌دریغ می‌بخشند و هیچ چشم‌داشتی ندارند.",
            etymology = "ریشه لاتین bene (خوب) + velle (خواستن)؛ یعنی «خوب خواستن».",
            aiHint = "مترادف‌های کلیدی: Kind و Generous.",
        ),
        payload = QuizPayload.MeaningChoice(
            options = listOf(
                QuizOption(id = "o1", text = "Benevolent", phonetic = "/bəˈnev.əl.ənt/", tag = "صفت"),
                QuizOption(id = "o2", text = "Malevolent", phonetic = "/məˈlev.əl.ənt/", meaning = "بدخواه و کینه‌توز"),
                QuizOption(id = "o3", text = "Ambiguous", phonetic = "/æmˈbɪɡ.ju.əs/", meaning = "مبهم و چندپهلو"),
                QuizOption(id = "o4", text = "Meticulous", phonetic = "/məˈtɪk.jə.ləs/", meaning = "بسیار دقیق و وسواسی"),
            ),
            correctOptionId = "o1",
        ),
    )

    /** Format 3: audio prompt -> pick the word that was heard. */
    private fun listeningQuestion() = QuizQuestion(
        id = "q_listening_ephemeral",
        type = QuizQuestionType.LISTENING,
        word = QuizWord(
            term = "Ephemeral",
            phonetic = "/ɪˈfem.ər.əl/",
            level = "C1",
            partOfSpeech = "صفت (Adjective)",
            meaning = "زودگذر، ناپایدار و کم‌دوام",
            definition = "چیزی که عمر کوتاهی دارد و به‌سرعت از بین می‌رود.",
            contextSentence = "The beauty of the cherry blossoms is ephemeral.",
            contextTranslation = "زیبایی شکوفه‌های گیلاس زودگذر است.",
            mnemonic = "ماندگاری کوتاه شکوفه‌های بهاری یا یک استوری ۲۴ ساعته.",
            aiHint = "این واژه معمولاً برای زیبایی‌های موقتی به کار می‌رود.",
        ),
        payload = QuizPayload.Listening(
            accent = "US",
            options = listOf(
                QuizOption(
                    id = "o1",
                    text = "Ephemeral",
                    phonetic = "/ɪˈfem.ər.əl/",
                    meaning = "زودگذر، ناپایدار و کم‌دوام",
                ),
                QuizOption(
                    id = "o2",
                    text = "Ethereal",
                    phonetic = "/ɪˈθɪr.i.əl/",
                    meaning = "آسمانی، اثیری و ماورایی",
                ),
                QuizOption(
                    id = "o3",
                    text = "Empirical",
                    phonetic = "/ɪmˈpɪr.ɪ.kəl/",
                    meaning = "تجربی، عملی و وابسته به مشاهده",
                ),
                QuizOption(
                    id = "o4",
                    text = "Equivocal",
                    phonetic = "/ɪˈkwɪv.ə.kəl/",
                    meaning = "مبهم، دوپهلو و نامشخص",
                ),
            ),
            correctOptionId = "o1",
        ),
    )

    /** Format 4: complete the missing letters. */
    private fun spellingQuestion() = QuizQuestion(
        id = "q_spelling_abandon",
        type = QuizQuestionType.SPELLING,
        word = QuizWord(
            term = "abandon",
            phonetic = "/əˈbæn.dən/",
            level = "B1",
            partOfSpeech = "فعل (Verb)",
            meaning = "رها کردن، ترک گفتن، واگذاشتن",
            definition = "چیزی یا کسی را بدون قصد بازگشت ترک کردن.",
        ),
        payload = QuizPayload.Spelling(
            answer = "abandon",
            revealedIndices = setOf(0, 1, 2, 4, 5),
            letterBank = listOf("A", "D", "B", "N", "O", "E", "M", "S"),
            hint = "این کلمه ۷ حرف دارد و از ریشه لاتین abandonare به معنای «رها کردن» می‌آید.",
        ),
    )

    /** Format 5: fill the blank inside a real sentence. */
    private fun sentenceBlankQuestion() = QuizQuestion(
        id = "q_sentence_abandon",
        type = QuizQuestionType.SENTENCE_BLANK,
        word = QuizWord(
            term = "abandon",
            phonetic = "/əˈbæn.dən/",
            level = "B2",
            partOfSpeech = "فعل • شکل ساده (Root Verb)",
            meaning = "رها کردن، ترک گفتن",
            definition = "در بافت اضطراری یعنی محل را ترک کردن و پشت سر گذاشتن.",
        ),
        payload = QuizPayload.SentenceBlank(
            sentence = "The emergency crew advised residents to abandon their homes before the severe flood reached the valley.",
            translation = "تیم امداد به ساکنان توصیه کرد پیش از رسیدن سیلاب شدید، خانه‌های خود را رها کنند.",
            options = listOf(
                QuizOption(id = "o1", text = "abandon", tag = "فعل • شکل ساده (Root Verb)"),
                QuizOption(id = "o2", text = "abundant", tag = "صفت (Adjective)", meaning = "فراوان"),
                QuizOption(id = "o3", text = "abandoned", tag = "صفت مفعولی (Past Participle)", meaning = "متروکه"),
                QuizOption(id = "o4", text = "abandonment", tag = "اسم (Noun)", meaning = "رهاسازی"),
            ),
            correctOptionId = "o1",
        ),
    )
}
