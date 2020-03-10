package com.duwan.slate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * @author duwan.zq
 * @date 2020/3/6
 */
public class MarkdownParser {

    public static Attribute parse(String resourcePath, List<String> includes, String markdown) throws IOException {
        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                JekyllTagExtension.create(),
                TocExtension.create(),
                SimTocExtension.create()
        )).set(TocExtension.LEVELS, 255)
          .set(TocExtension.TITLE, "Table of Contents")
          .set(TocExtension.DIV_CLASS, "toc")
          .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "");


        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        for(String includeFileName : includes){
            Node includeDoc = parser.parse(Utils.readStringFromResource(resourcePath + "/" + includeFileName));
            html += renderer.render(includeDoc);
        }

        //contents
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("h1");

        List<Content> contents = new ArrayList<>();

        Element nextElement = null;

        int index = 0;
        for(Element element : elements){
            if(index < elements.size() - 1){
                nextElement = elements.get(index + 1);
            }
            List<Content> children = new ArrayList<>();

            Element childElement = element.nextElementSibling();
            while (childElement!= null && !childElement.equals(nextElement)){
                if(childElement.tagName().equals("h2")){
                    children.add(convertContent(childElement));
                }
                childElement = childElement.nextElementSibling();
            }

            contents.add(convertContent(element, children));
            index ++;
        }



        return Attribute.builder().contents(contents).html(html).build();
    }

    private static Content convertContent(Element element){
        return Content.builder()
                      .id(element.id())
                      .content(element.childNodes().toString())
                      .title(element.childNodes().get(0).toString())
                      .level(Integer.parseInt(element.tagName().substring(1)))
                      .build();
    }

    private static Content convertContent(Element element, List<Content> children){
        return Content.builder()
                      .id(element.id())
                      .content(element.childNodes().toString())
                      .title(element.childNodes().get(0).toString())
                      .level(Integer.parseInt(element.tagName().substring(1)))
                      .children(children)
                      .build();
    }

}
