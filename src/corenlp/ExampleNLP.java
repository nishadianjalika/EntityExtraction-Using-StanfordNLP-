package corenlp;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Properties;

import javax.sql.rowset.spi.XmlWriter;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class ExampleNLP {
	
	public static void main(String args[]) throws IOException{
		PrintWriter out;
		if(args.length > 1){
			out=new PrintWriter(args[1]);	
		}
		else{
			out=new PrintWriter(System.out);
		}
		
		PrintWriter xmlout=null;
		if(args.length >2){
			xmlout= new PrintWriter(args[2]);
		}
		
		//creates stanford core NLP object, with POS tagging, lemmatization,parsing,coreference resolution
		Properties props=new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref");
		StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
		
		String text="As a User Admin, I need to be able to add a new user group to the system, so that I can add users to that group and manage them easily.";
		
		Annotation document=new Annotation(text); //creates an empty annotation with the given text string//
		
		pipeline.annotate(document); //run all annotations in this text//
		pipeline.prettyPrint(document, out);
		if(xmlout != null){
			pipeline.xmlPrint(document, xmlout);
		}
		
		//sentences//
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		System.out.println("sentences are: " + sentences);
		
		for(CoreMap sentence: sentences){
			for(CoreLabel token: sentence.get(TokensAnnotation.class)){
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);
			}
			
			Tree tree=sentence.get(TreeAnnotation.class);
			
			SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			
		}
		
		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
	}

}
