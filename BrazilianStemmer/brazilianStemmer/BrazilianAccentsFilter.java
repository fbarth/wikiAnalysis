package brazilianStemmer;


import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class BrazilianAccentsFilter extends TokenFilter
{
    private Token token = null;

    public BrazilianAccentsFilter(TokenStream input)
    {
        super(input);
    }

    @Override
    public Token next() throws IOException
    {
        token = input.next();
        if (token == null) {
            return null;
        }
        
        String s = token.termText();
        s = removeAccents(s);
        return new Token(s, token.startOffset(), token.endOffset(), token.type());
    }
    
    public static String removeAccents(String s)
    {
        StringBuilder builder = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'á':
                case 'ã':
                case 'â':
                case 'à':
                    c = 'a';
                    break;
                case 'é':
                case 'è':
                case 'ê':
                    c = 'e';
                    break;
                case 'í':
                case 'ì':
                case 'î':
                    c = 'i';
                    break;
                case 'ó':
                case 'ò':
                case 'ô':
                case 'õ':
                    c = 'o';
                    break;
                case 'ú':
                case 'ù':
                case 'û':
                case 'ü':
                    c = 'u';
                    break;
            }
            builder.append(c);
        }
        s = builder.toString();
        return s;
    }
}
