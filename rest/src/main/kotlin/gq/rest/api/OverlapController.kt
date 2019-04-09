package gq.rest.api

import gq.core.data.Species
import gq.rest.services.OverlapResponse
import gq.rest.services.OverlapService
import gq.rest.validators.OverlapRequestFormValidator
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController(OverlapController.URL)
open class OverlapController @Autowired constructor(val overlapService: OverlapService) {

    companion object {
        val LOG = LogManager.getLogger(OverlapController::class.java)
        const val URL = "/find-overlap"

    }

    class OverlapRequestForm {
        var genes: List<String>? = null
        var speciesFrom: String? = null
        var speciesTo: String? = null
        var moduleName: String? = null
    }

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(OverlapRequestFormValidator())
    }

    @RequestMapping(
        value = [URL],
        produces = arrayOf("application/json"),
        method = arrayOf(RequestMethod.POST)
    )
    @ResponseStatus(HttpStatus.OK)
    fun findOverlap(@Validated @RequestBody form: OverlapRequestForm): OverlapResponse {
        LOG.info(
            "Request accepted: speciesTo=${form.speciesTo},speciesFrom=${form.speciesFrom},module=${form.moduleName},genes=${form.genes?.joinToString(
                " "
            )}"
        )
        val overlapResponse = overlapService.findOverlap(
            form.genes!!,
            Species.fromOriginal(form.speciesFrom!!),
            Species.fromOriginal(form.speciesTo ?: form.speciesFrom!!),
            form.moduleName!!
        )
        LOG.info("Overlap result: ${overlapResponse.overlapSymbolGenes.size}")
        return overlapResponse
    }
}
