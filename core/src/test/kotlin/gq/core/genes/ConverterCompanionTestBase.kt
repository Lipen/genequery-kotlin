package gq.core.genes

import java.io.File

open class ConverterCompanionTestBase {
    fun listOfL(vararg elements: Int?) = elements.map { it?.toLong() }
    fun listOfL(vararg elements: Int) = elements.map { it.toLong() }

    fun getPath(fileName: String): String = this.javaClass.getResource("/converter/$fileName").path

    fun readMappings(fileName: String, geneFormat: GeneFormat) =
        File(getPath(fileName)).readAndNormalizeGeneMappings(geneFormat)

    fun createFromEntrezToSymbolConverter() =
        FromEntrezToSymbolConverter(readMappings("symbol-to-entrez.txt", GeneFormat.SYMBOL))

    fun createToEntrezConverter() = ToEntrezConverter()
        .populate { ToEntrezNormalizeConverterKtTest.readMappings("refseq-to-entrez.txt", GeneFormat.REFSEQ) }
        .populate { ToEntrezNormalizeConverterKtTest.readMappings("symbol-to-entrez.txt", GeneFormat.SYMBOL) }
        .populate { ToEntrezNormalizeConverterKtTest.readMappings("ensembl-to-entrez.txt", GeneFormat.ENSEMBL) }
}

