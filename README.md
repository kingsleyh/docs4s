## Docs4s

[![Build Status](https://travis-ci.org/kingsleyh/docs4s.png?branch=master)](https://travis-ci.org/kingsleyh/docs4s) [ ![Download](https://api.bintray.com/packages/kingsleyh/repo/docs4s/images/download.svg) ](https://bintray.com/kingsleyh/repo/docs4s/_latestVersion)

This project generates documentation for code. Rather than using comments embedded in the code Docs4s requires you to write the documentation separately
using it's DSL-like API. I didn't want to litter my code with comments and this is my alternative approach based on a similar idea by the great and powerful [Stacy Curl](https://github.com/stacycurl)

The output templates have been re-purposed from the [Ramdajs](http://ramdajs.com/0.19.1/docs/) project. So really I didn't do much since I took the general idea 
from Stacy and the html/css/javascript from Ramdajs.

### API Documentation

In the general spirit of eating my own - the documentation for this project has been generated using this project itself.

[Documentation](https://rawgit.com/kingsleyh/docs4s/master/docs/documentation.html)

### Example

Here is the code that generated this documentation:

       
        object Docs extends App {
        
          val cards = List(
            Card("new", "Creates a new instance of the DocGen class.", "DocGen",
              List(Param("String", "name", "(project name)", ParamType.INPUT),
                Param("String", "version", "(project version)", ParamType.INPUT),
                Param("String", "githubUrl", "(base url to github branch e.g. master)", ParamType.INPUT),
                Param("DocGen", "", "", ParamType.OUTPUT)
              ),
              List(Example(
                """
                  |new DocGen("Docs4s","v0.0.1")
                  |       .withLinks(links)
                  |       .withCards(cards)
                  |       .withPages(pages)
                  |       .generate()
                """.stripMargin)),
              List.empty, "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala")
            ,
            Card("withLinks", "Sets the links on the top navbar.", "DocGen", List(
              Param("List[Link]", "links", "(a list of Link case classes)", ParamType.INPUT),
              Param("DocGen", "", "", ParamType.OUTPUT)
            ),
              List(Example(
                """
                  |val links = List(
                  |      Link("Home","home.html"),
                  |      Link("Documentation","#","active"),
                  |      Link("Github","https://github.com/kingsleyh/docs4s")
                  |    )
                  |new DocGen("Docs4s","v0.0.1").withLinks(links)
                """.stripMargin)),
              List(Link("Link", "#Link")), "source/docs4s.d"
            ),
            Card("withCards", "Specifies the documentation for each item.", "DocGen", List(
              Param("List[Card]", "cards", "(a list of Card case classes)", ParamType.INPUT),
              Param("DocGen", "", "", ParamType.OUTPUT)
            ),
              List(Example(
                """
                  |val cards = List(
                  |       Card("new","Creates a new instance of the DocGen class.","DocGen",List(
                  |         Param("String","name","(project name)",ParamType.INPUT),
                  |         Param("String","version","(project version)",ParamType.INPUT),
                  |         Param("DocGen","","",ParamType.OUTPUT)
                  |         ),List(
                  |           Example(
                  |           "new DocGen("Docs4s","v0.0.1")\n"
                  |           "     .withLinks(links)       \n"
                  |           "     .withCards(cards)       \n"
                  |           "     .withPages(pages)       \n"
                  |           "     .generate()             \n"
                  |           )
                  |         ),List(Link("Card","#Card"))));
                  |new DocGen("Docs4s","v0.0.1").withCards(cards)
                """.stripMargin))
              , List(Link("Card", "#Card"), Link("Param", "#Param"), Link("Example", "#Example")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("withPages", "Specifies additional pages.", "DocGen", List(
              Param("List[Page]", "pages", "(a list of Page case classes)", ParamType.INPUT),
              Param("DocGen", "", "", ParamType.OUTPUT)
            ),
              List(Example(
                """
                  |val pages = List(
                  |  Page("home.html","Home",List(
                  |    Link("Home","home.html","active"),
                  |    Link("Documentation","documentation.html"),
                  |    Link("Github","https://github.com/kingsleyh/docs4s")
                  |    ),"README.md")
                  |)
                """.stripMargin)), List(Link("Page", "#Page"), Link("Link", "#Link")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("generate", "generates the html files", "void", List.empty, List.empty, List.empty, "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("Link", "Case class that holds link information.", "Link", List(
              Param("String", "text", "", ParamType.INPUT),
              Param("String", "url", "", ParamType.INPUT),
              Param("String", "active", "(leave blank if link is not the active page, otherwise use text 'active')", ParamType.INPUT),
              Param("Link", "", "", ParamType.OUTPUT)
            ), List(Example(
              """
                | Link("Home","home.html")
              """.stripMargin)),
              List(Link("withLinks", "#withLinks"), Link("withPages", "#withPages"), Link("withCards", "#withCards")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("Card", "Case class that holds documentation information.", "Card", List(
              Param("String", "name", "", ParamType.INPUT),
              Param("String", "description", "", ParamType.INPUT),
              Param("String", "category", "", ParamType.INPUT),
              Param("List[Param]", "params", "", ParamType.INPUT),
              Param("List[Example]", "examples", "", ParamType.INPUT),
              Param("Card", "", "", ParamType.OUTPUT)
            ), List(Example(
              """
                | Card("new","creates a new instance of the DocGen class","DocGen",List(
                |         Param("String","name","(project name)",ParamType.INPUT),
                |         Param("String","version","(project version)",ParamType.INPUT),
                |         Param("DocGen","","",ParamType.OUTPUT)
                |         ),List(
                |           Example(
                |           "new DocGen("Docs4s","v0.0.1")\n"
                |           "     .withLinks(links)       \n"
                |           "     .withCards(cards)       \n"
                |           "     .withPages(pages)       \n"
                |           "     .generate()             \n"
                |           )
                |         )));
              """.stripMargin)),
              List(Link("withCards", "#withCards")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("Param", "Case class that holds parameter info.", "Param", List(
              Param("String", "type", "", ParamType.INPUT),
              Param("String", "id", "", ParamType.INPUT),
              Param("String", "description", "", ParamType.INPUT),
              Param("ParamType", "ParamType", "", ParamType.INPUT),
              Param("Param", "", "", ParamType.OUTPUT)
            ), List(
              Example(
                """
                  |Param("String","name","(project name)",ParamType.INPUT)
                """.stripMargin)
            ), List(Link("Card", "#Card"), Link("ParamType", "#ParamType")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("Example", "Case class that holds example info.", "Example", List(
              Param("String", "code", "", ParamType.INPUT),
              Param("Example", "", "", ParamType.OUTPUT)
            ), List(
              Example(
                """
                  |Example("val doc = new DocGen(\"Docs4s\",\"v0.0.1\")")
                """.stripMargin)
            ), List.empty, "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala"),
            Card("ParamType", "Case class that holds parameter type info for params.", "ParamType", List(
              Param("ParamType", "", "(ParamType.INPUT,ParamType.OUTPUT)", ParamType.OUTPUT)
            ), List(Example(
              """
                |Param("String","name","(project name)",ParamType.INPUT)
              """.stripMargin)), List(Link("Param", "#Param")), "src/main/scala/net/kenro/ji/jin/docs/Docs4s.scala")
          )
        
          val links = List(
            Link("Home", "home.html"),
            Link("Documentation", "#", "active"),
            Link("Github", "https://github.com/kingsleyh/docs4s")
          )
        
          val pages = List(
            Page("home.html", "Home", List(
              Link("Home", "home.html", "active"),
              Link("Documentation", "documentation.html"),
              Link("Github", "https://github.com/kingsleyh/docs4s")
            ), "README.md")
          )
        
          new DocGen("Docs4s", "v9", "https://github.com/kingsleyh/docs4s/blob/master")
            .withLinks(links)
            .withCards(cards)
            .withPages(pages)
            .generate()
        
        
        }