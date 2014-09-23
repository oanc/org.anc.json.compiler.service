package org.anc.json.compiler.service

import org.anc.json.compiler.SchemaCompiler
import org.anc.json.compiler.SchemaException
import org.anc.web.WebUtil

import javax.ws.rs.Consumes
import javax.ws.rs.DefaultValue
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author Keith Suderman
 */

@Path("/")
class CompilerService {
    SchemaCompiler compiler

    public CompilerService() {
        compiler = new SchemaCompiler()
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response compile(
            @QueryParam('draft')
            @DefaultValue('4')
            String draft,
            @QueryParam('pretty')
            @DefaultValue('true')
            String prettyPrint,
            String inputJson) {
        try {
            compiler.draftVersion = draft as int
        }
        catch (NumberFormatException e) {
            return WebUtil.error("Invalid draft version specified. The draft version must be an integer value.")
        }
        if (prettyPrint == 'true') {
            compiler.prettyPrint = true
        }
        else if (prettyPrint == 'false') {
            compiler.prettyPrint = false
        }
        else {
            return WebUtil.error('Invalid value for the "pretty" parameter. Must be one of "true" or "false"')
        }

        try {
            compiler.prettyPrint = prettyPrint == 'true'
            String outputJson = compiler.compile(inputJson)
            return WebUtil.json(outputJson)
        }
        catch (SchemaException e) {
            return WebUtil.error(e.getMessage())
        }
    }
}
