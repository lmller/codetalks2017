package io.github.lmller.codetalks2017;

import java.util.Collections;
import java.util.List;

public class Models {
	public static class MetaData {
		public String apiVersion;
		public String warning;
		public String copyright;
		public String license;
		public String source;
		public String date;
	}

	public static class SynonymList {
		public MetaData metaData;
		public List<Synset> synsets = Collections.emptyList();
	}

	public static class Synset {
		public long id;
		public List<String> categories = Collections.emptyList();
		public List<Term> terms = Collections.emptyList();

	}

	public static class Term {
		public String term;
	}
}
