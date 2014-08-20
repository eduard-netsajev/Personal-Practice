__author__ = 'Netšajev'


from PIL import Image, ImageDraw


def get_wave_lenght_from_data_point(value, min_value, max_value):
    """
    Normalize values to wavelength value so we
    can use it for wave_length_to_rgb function
    """
    #visible wave lengths
    min_wave = 380.0
    max_wave = 780.0

    #   Data normalization: Convert data value in the
    #   range of min_values..max_values to the range 380..780
    result = (value - min_value) / (max_value-min_value) * (max_wave - min_wave) + min_wave
    return result


def wave_length_to_rgb(wave_length):
    gamma = 0.80
    intensity_max = 255

    """ Taken from Earl F. Glynn's web page Spectra Lab Report:
    http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm
    """

    if 380 <= wave_length < 781:
        if wave_length < 440:
            red = -(wave_length - 440) / (440 - 380)
            green = 0.0
            blue = 1.0
        elif wave_length < 490:
            red = 0.0
            green = (wave_length - 440) / (490 - 440)
            blue = 1.0
        elif wave_length < 510:
            red = 0.0
            green = 1.0
            blue = -(wave_length - 510) / (510 - 490)
        elif wave_length < 580:
            red = (wave_length - 510) / (580 - 510)
            green = 1.0
            blue = 0.0
        elif wave_length < 645:
            red = 1.0
            green = -(wave_length - 645) / (645 - 580)
            blue = 0.0
        else:
            red = 1.0
            green = 0.0
            blue = 0.0
    else:
        red = 0.0
        green = 0.0
        blue = 0.0

    # Let the intensity fall off near the vision limits
    if 380 <= wave_length:
        if wave_length < 420:
            factor = 0.3 + 0.7*(wave_length - 380) / (420 - 380)
        elif wave_length < 701:
            factor = 1.0
        elif wave_length < 781:
            factor = 0.3 + 0.7*(780 - wave_length) / (780 - 700)
    else:
        factor = 0.0

    # Don't want 0^x = 1 for x <> 0
    rgb = (0 if red == 0 else int((intensity_max * ((red * factor) ** gamma)) // 1),
           0 if green == 0 else int((intensity_max * ((green * factor) ** gamma)) // 1),
           0 if blue == 0 else int((intensity_max * ((blue * factor) ** gamma)) // 1))

    return rgb


def gen_word(opened_file):
    """Generate next word"""
    result = ""
    for line in opened_file:
        for char in line:
            if char.isalpha():
                result += char
            else:
                word = result
                # > 1 because we need only 2+ chars containing words
                if len(word) > 1:
                    result = ""
                    yield word


def create_matrix(words_list):
    """
    create 2 dimensional matrix, where row is
    first letter and column is second letter

    :param words_list: list of words
    :return: matrix 26x26
    """
    matrix = [[0]*26 for i in range(26)]

    for word in words_list:
        word = word.lower()
        for i in range(1, len(word)):
            first_index = ord(word[i-1])-97
            second_index = ord(word[i])-97
            matrix[first_index][second_index] += 1
    return matrix


def generate_heatmap(data_matrix):

    min_freq = 10000
    max_freq = 0

    def draw_heatmap_rgb(dataset, mode):
        tile_size = 20
        im = Image.new(mode, (26*tile_size, 26*tile_size))
        draw = ImageDraw.Draw(im)
        for d in dataset:
            if mode == "RGB":
                draw.rectangle([(d[0]*tile_size, d[1]*tile_size), ((d[0]+1)*tile_size, (d[1]+1)*tile_size)],
                               wave_length_to_rgb(get_wave_lenght_from_data_point(d[2], min_freq, max_freq)))
            elif mode == "L":
                draw.rectangle([(d[0]*tile_size, d[1]*tile_size), ((d[0]+1)*tile_size, (d[1]+1)*tile_size)],
                               255*((d[2]-min_freq)/(max_freq-min_freq)))
        # write file
        im.save("heatmap_%s.png" % mode, "PNG")

    data = []
    size = len(data_matrix)

    for i in range(size):
        for j in range(size):
            value = data_matrix[i][j]
            if value > max_freq:
                max_freq = value
            if value < min_freq:
                min_freq = value

    for i in range(size):
        for j in range(size):
            data.append((j, i, data_matrix[i][j]))

    draw_heatmap_rgb(data, "RGB")
    draw_heatmap_rgb(data, "L")


def main():
    my_file = open("Carcosa.txt")
    input_list = [word for word in gen_word(my_file)]
    generate_heatmap(create_matrix(input_list))

if __name__ == "__main__":
    main()