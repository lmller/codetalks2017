package io.github.lmller.codetalks2017


data class MetaData(
    var apiVersion: String? = null,
    var warning: String? = null,
    var copyright: String? = null,
    var license: String? = null,
    var source: String? = null,
    var date: String? = null
)

data class SynonymList (
    var metaData: MetaData? = null,
    var synsets: List<Synset> = emptyList()
)
data class Synset(
    var id: Long = 0,
    var categories: List<String> = emptyList(),
    var terms: List<Term> = emptyList()
)

data class Term(var term: String)

