package com.wbq.spring.beans.factory.config.support;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.wbq.spring.beans.PropertyValue;
import com.wbq.spring.beans.factory.config.BeanDefinition;
import com.wbq.spring.beans.factory.config.BeanReference;
import com.wbq.spring.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		InputStream inputStream = getResourceLoader().getInputStreamSource(location).getInputStream();
		doLoadBeanDefinitions(inputStream);
	}

	private void doLoadBeanDefinitions(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document doc = documentBuilder.parse(inputStream);

		//parse bean
		registerBeanDefinitions(doc);
		inputStream.close();
	}

	private void registerBeanDefinitions(Document doc) {
		Element root = doc.getDocumentElement();
		parseBeanDefinition(root);
	}

	private void parseBeanDefinition(Element root) {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}

	/**
	 * 解析bean标签
	 * @param ele
	 */
	private void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("name");
		String className = ele.getAttribute("class");

		BeanDefinition bd = new BeanDefinition();
		processProperty(ele, bd);
		bd.setBeanClassName(className);

		getRegistry().put(name, bd);
	}

	/**
	 * 解析property属性
	 * @param ele
	 * @param bd
	 */
	private void processProperty(Element ele, BeanDefinition bd) {
		NodeList propertyNode = ele.getElementsByTagName("property");
		for (int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");

				if (value != null && value.length() > 0) {
					bd.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				}
				else {
					String ref = propertyEle.getAttribute("ref");
					if (ref == null || ref.length() == 0) {
						throw new IllegalArgumentException("configuration problem:<property> element for property " + name + " must specify a ref or value");
					}
					BeanReference br = new BeanReference(ref);
					bd.getPropertyValues().addPropertyValue(new PropertyValue(name, br));
				}

			}
		}
	}
}
