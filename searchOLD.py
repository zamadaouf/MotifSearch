from nltk.tokenize import word_tokenize
from nltk.stem.isri import ISRIStemmer
from nltk.corpus import stopwords
from nltk.tag import pos_tag
from string import punctuation 
from io import open

# ----- Initialiser ----- #
f = open("motif.txt", "r", encoding='utf8')
motif = f.read(); f.close()  # !!! motif
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
motifLemma = lemma.stem(motif)  # !!! lemma du motif

tabLemma = []  # !!! lemma du text
for word in tabText:
    wordL = lemma.stem(word)
    tabLemma.append(wordL)

# ----- POS (part of speech) ----- #
motifPOS = ""  # !!! POS du motif
motifPOS = pos_tag([motifLemma])

# ----- Recherche de motif ----- #
tabMotifs = []  # !!! occurences du motif dans le text
occ = 0
for i in range(len(tabLemma)):
    if motifLemma.lower() == tabLemma[i].lower():
        tabMotifs.append([occ + 1, tabText[i], i])
        occ += 1

motifsStats = [motif, motifLemma, occ, (motifPOS[0])[1]]  # !!! infos sur la recherche effectué

# ----- Results ----- #
result = ''
for cell in tabMotifs:
    result += str(cell[0]) + ',' + cell[1] + ',' + str(cell[2]) + '\n'

f = open("resTxt.txt", "w", encoding="utf8"); f.write(result); f.close()

result = ''
result = str(motifsStats[0]); i = 1
while i < len(motifsStats):
        result += ',' + str(motifsStats[i])
        i += 1

f = open("resMot.txt", "w", encoding="utf8"); f.write(result); f.close()

# ----- Fin ----- #
