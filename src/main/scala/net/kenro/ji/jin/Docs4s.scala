package net.kenro.ji.jin

import com.vspy.mustache.Mustache
import org.fusesource.scalamd.Markdown
import scalax.file.Path


case class Param(kind: String, id: String, description: String, paramType: ParamType)

case class Example(code: String)

case class ParamType(value: String)
object ParamType {
  val INPUT = ParamType("INPUT")
  val OUTPUT = ParamType("OUTPUT")
}

case class Link(text: String, url: String, active: String = "")

case class Page(filename: String, name: String, links: List[Link], markdownFile: String) {
  def html: String = {
     Markdown(markdownFile)
  }
}

case class Card(name: String, description: String, category: String,
                params: List[Param], examples: List[Example], links: List[Link], fileLocation: String)

class DocGen(name: String, version: String, githubUrl: String) {

  var cards: List[Card] = List.empty
  var links: List[Link] = List.empty
  var pages: List[Page] = List.empty

 def withCards(cards: List[Card]): DocGen = {
    this.cards = cards
    this
 }

 def withLinks(links: List[Link]): DocGen = {
   this.links = links
   this
 }

 def withPages(pages: List[Page]) : DocGen = {
   this.pages = pages
   this
 }

 def generate(): Unit = {

   val context = scala.collection.mutable.Map[String, Any]()
   context("name") = name
   context("ver") = version

   context("links") = links.map(link => Map("text" -> link.text, "url" -> link.url, "active" -> link.active))

   context("cards") = cards.map(card => {
     val sub = scala.collection.mutable.Map[String, Any]()
     sub("name") = card.name
     sub("category") = card.category
     sub("description") = card.description

     // params
     if(card.params.nonEmpty){
       sub("signature") = card.params.map(p => p.kind).mkString(" &#8594; ")
       sub("has_params") = true

       card.params.foreach(param => {
         if(param.paramType == ParamType.INPUT){
           sub("input_params") = List(Map("type" -> param.kind, "id" -> param.id, "desc" -> param.description))
         } else {
           sub("return_value") = List(Map("has_return" -> true, "type" -> param.kind, "desc" -> param.description))
         }
       })
     }

     // examples
     if(card.examples.nonEmpty){
       sub("has_examples") = true
       sub("examples") = card.examples.map(example => Map("code" -> example.code))
     }

     // links
     if(card.links.nonEmpty){
       sub("has_see_also") = true
       sub("see_also") = card.links.map(link => Map("text" -> link.text, "url" -> link.url))
     }

     if(card.fileLocation.nonEmpty){
       sub("has_gh") = true
       sub("gh") = s"$githubUrl/${card.fileLocation}"
     }

     sub

   })

   // generate docs
   val path: Path = Path("docs")
   if(path.exists){
     path.deleteRecursively(continueOnFailure = true)
   }

   path.createDirectory()

   Path.fromString(getClass.getResource("/main.js").getPath).copyTo(Path.fromString("docs/main.js"))
   Path.fromString(getClass.getResource("/style.css").getPath).copyTo(Path.fromString("docs/style.css"))
   Path.fromString("docs/fonts").createDirectory()
   Path.fromString(getClass.getResource("/fonts/glyphicons-halflings-regular.woff2").getPath).copyTo(Path.fromString("docs/fonts/glyphicons-halflings-regular.woff2"))

   val documentation = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/templates/documentation.mustache")).mkString
   val template = new Mustache(documentation)
   val html = template.render(context)

   Path.fromString("docs/documentation.html").write(html)

   // gen pages
   pages.foreach(page => {
     val pageContext = scala.collection.mutable.Map[String, Any]()
     pageContext("page_links") = page.links.map(link => Map("text" -> link.text, "url" -> link.url, "active" -> link.active))
     pageContext("page_html") = page.html

     val pageTemplate = new Mustache(scala.io.Source.fromInputStream(getClass.getResourceAsStream("/templates/page.mustache")).mkString)
     val pageHtml = pageTemplate.render(pageContext)
     Path.fromString(s"docs/${page.filename}").write(pageHtml)

   })

 }


}

object A extends App {
  val d = new DocGen("test","0.0.1","http://some/url")
  d.withCards(List(
    Card("new","Creates a new instance of the DocGen class.","DocGen",
      List(
        Param("string","name","(project name)", ParamType.INPUT),
        Param("DocGen","","",ParamType.OUTPUT)
      ),
      List(
        Example("new DocGen(){}")
      ),
      List(
        Link("Link","#Link")
      ),
    "source/docs4s.scala")
  ))
    .withLinks(List(
      Link("Home","home.html")
    ))
    .withPages(List(
      Page("home.html","Home", List(
        Link("Home","home.html","active")
      ), "README.md")
    ))
    .generate()
}


