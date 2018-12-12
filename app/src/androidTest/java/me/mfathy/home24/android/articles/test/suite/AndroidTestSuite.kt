package me.mfathy.home24.android.articles.test.suite

import me.mfathy.home24.android.articles.ui.review.ReviewActivityTest
import me.mfathy.home24.android.articles.ui.select.SelectActivityTest
import me.mfathy.home24.android.articles.ui.start.StartActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Mohammed Fathy on 12/12/2018.
 * dev.mfathy@gmail.com
 *
 * Test suite that runs all UI espresso tests.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(StartActivityTest::class, SelectActivityTest::class, ReviewActivityTest::class)
class AndroidTestSuite