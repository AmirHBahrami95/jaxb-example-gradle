package org.example.model;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@XmlRootElement(name="Zombie")
public class Zombie{
	
	@XmlTransient private String name;
	@XmlTransient private int hp;
	@XmlTransient private List<String> eaten;

	public Zombie(){}
	
	public Zombie(String name,int hp){
		this.name=name;
		this.hp=hp;
		this.eaten=new ArrayList<String>();
	}

	public void eat(String targetsName){
		
		if(hp<=0){
			System.err.println(this.name+" is full (dead as fuck).");
			return;
		}

		this.eaten.add(targetsName);
		hp-=10;
	}
	
	@XmlElement(name="name") public String getName(){return this.name;}
	@XmlElement(name="hp") public int getHp(){return this.hp;}

	@XmlElementWrapper(name="EatRecords")
	@XmlElement(name="human") public List<String> getEaten(){return this.eaten;}

	public void marshalTo(String path) throws JAXBException,IOException{
		JAXBContext cx=JAXBContext.newInstance(Zombie.class);
		Marshaller m=cx.createMarshaller();
		File fout=new File(path);
		if(fout.isDirectory())throw new IOException("'"+path+"' is a directory");
		if(!fout.exists())fout.createNewFile();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		m.marshal(this,fout);
	}
}
