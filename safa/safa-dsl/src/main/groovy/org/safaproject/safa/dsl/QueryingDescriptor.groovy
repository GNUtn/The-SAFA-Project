// this is a DSLD file
// start off creating a custom DSL Descriptor for your Groovy DSL

// The following snippet adds the 'newProp' to all types that are a subtype of GroovyObjects
// currentType(subType('groovy.lang.GroovyObject')).accept {
//   property name : 'newProp', type : String, provider : 'Sample DSL', doc : 'This is a sample.  You should see this in content assist for GroovyObjects: <pre>newProp</pre>'
// }


package org.safaproject.safa.dsl

import org.safaproject.safa.dsl.filter.Filter

class QueryingDescriptor {
	def parse(String query){
		def shell = new GroovyShell(this.binding())
		return shell.evaluate(query)
	}

	def search = new FilterBuilder()

	def binding() {
		return new Binding([
			tipo: new Filter("tipo"),
		])
	}
}
