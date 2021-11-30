package hu.aschlichter.myxmlapp.config;

import hu.aschlichter.myxmlapp.model.Person;
import hu.aschlichter.myxmlapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {

    private final PersonRepository personRepository;

    public AppConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Value("${xml.datasource}")
    private String inputData;

    @Bean
    public void xmlReader() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<String> resultList = new ArrayList<>();
        Map<String, Integer> resultMap = new HashMap<>();
        Person person = new Person();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputData);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile("//datafield[@tag=\"100\"]/subfield[@code=\"a\"]");

            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                resultList.add(node.getTextContent());
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        for (String tempString : resultList) {
            int count = resultMap.containsKey(tempString) ? resultMap.get(tempString) : 0;
            resultMap.put(tempString, count + 1);
        }

        int id = 0;

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            id++;
            person.setId(id);
            person.setName(entry.getKey());
            person.setAppearance(entry.getValue());
            personRepository.save(person);
        }
    }

}
