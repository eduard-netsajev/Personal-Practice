__author__ = 'Netšajev'


def clean_input(text):
    result = ""
    for char in text:
        if char.isalnum() or char == " ":
            result += char
    return result.strip()


def scan(text):
    text = clean_input(text).lower()
    lexicon_dict = {
        'direction': ['north', 'south', 'east', 'west', 'up', 'down', 'left', 'right', 'back'],
        'verb': ['go', 'stop', 'kill', 'eat', 'attack', 'open'],
        'stop': ['the', 'in', 'of', 'from', 'at', 'it', 'to'],
        'noun': ['door', 'bear', 'princess', 'cabinet'],
    }
    result = []
    words = text.split()
    for word in words:
        for word_type in lexicon_dict:
            if word in lexicon_dict[word_type]:
                result.append((word_type, word))
                break
        else:
            try:
                num = int(word)
                result.append(('number', num))
            except ValueError:
                result.append(('error', word))

    return result