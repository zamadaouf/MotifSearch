from nltk.tokenize import word_tokenize
from nltk.stem.isri import ISRIStemmer
from nltk.corpus import stopwords
from nltk.tag import pos_tag
from string import punctuation 
from io import open

# ----- Initialiser ----- #
f = open("text.txt", "r", encoding='utf8')
text = f.read(); f.close()
f = open("isArab.txt", "r", encoding='utf8')
isArab = f.read(); f.close()

# ----- Tokeniser ----- #
text = word_tokenize(text)

# ----- Enlever stop words ----- #
tmp = []
if isArab == 'n':
    for word in text:
        if word not in stopwords.words('english'):
            tmp.append(word)
elif isArab == 'y':
    for word in text:
        if word not in stopwords.words('arabic'):
            tmp.append(word)

# ----- Enlever la punctuation ----- #
tabText = []  # !!! mots du text bien tokenisé et nettoyé des tokens non-important
for word in tmp:
    if (word in punctuation) or (len(word) <= 1):
        continue
    for char in word:
        if char in punctuation:
            word = word.replace(char, '')
        if char in '؟، ًٌٍََُِّْ ':
            word = word.replace(char, '')
    tabText.append(word)

# ----- Lemmatiser ----- #
lemma = ISRIStemmer()
tabLemma = []  # !!! lemma du text
for word in tabText:
    wordL = lemma.stem(word)
    tabLemma.append(wordL)

# ----- Transfère vers java ----- #
result = ''
for word in tabText:
    result += str(word) + '\n'

f = open("resText.txt", "w", encoding="utf8"); f.write(result); f.close()

result = ''
for word in tabLemma:
    result += str(word) + '\n'

f = open("resLemma.txt", "w", encoding="utf8"); f.write(result); f.close()

# ----- Fin ----- #
