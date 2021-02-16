package com.uu.searchengine.handlers

import com.uu.searchengine.BeanUtil
import com.uu.searchengine.dto.WebPageDto
import com.uu.searchengine.entity.*
import com.uu.searchengine.service.*
import com.uu.searchengine.utils.Normalizer
import com.uu.searchengine.utils.Tokenizer
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component

@Component
object Handler {

    private val termService: TermService = BeanUtil.getBean(TermService::class.java)

    private val allTermStringsHashMap = hashMapOf<String,Int>()

    fun addWebPageDto(webPageDto: WebPageDto) {
        val termStrings = Tokenizer.normalizeThenTokenizeToList(webPageDto.body)

        for (termString in termStrings)
        {
            allTermStringsHashMap.get(termString)?.let {
                allTermStringsHashMap.put(termString,it+1)
            } ?: kotlin.run {
                allTermStringsHashMap.put(termString,1)
            }
        }

    }

    fun addAllDiscoveredTermsToDb()
    {
        var terms = arrayListOf<Term>()

        println("distinct tokens : ${allTermStringsHashMap.size}")

        for (termString in allTermStringsHashMap.keys)
        {
            val term = Term(termString , allTermStringsHashMap[termString]!!)
            terms.add(term)
        }

        println("done")

        termService.saveTerms(terms)
    }

}