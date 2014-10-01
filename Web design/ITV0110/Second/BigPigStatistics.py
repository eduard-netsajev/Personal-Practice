__author__ = 'Netšajev'


from PIL import Image, ImageDraw
import BigPig


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
    result = (value - min_value) / (max_value+0.1-min_value) * (max_wave - min_wave) + min_wave
    return result


def wave_length_to_rgb(wave_length):
    gamma = 0.950
    intensity_max = 255

    """ Taken from Earl F. Glynn's web page Spectra Lab Report:
    http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm
    """

    if 380 <= wave_length < 781:
        if wave_length < 460:
            red = -(wave_length - 460) / (460 - 380)
            green = 0.0
            blue = 1.0
        elif wave_length < 580:
            red = 0.0
            green = (wave_length - 460) / (580 - 460)
            blue = 1.0
        elif wave_length < 620:
            red = 0.0
            green = 1.0
            blue = -(wave_length - 620) / (620 - 560)
        elif wave_length < 690:
            red = (wave_length - 570) / (690 - 570)
            green = 1.0
            blue = 0.0
        elif wave_length < 755:
            red = 1.0
            green = -(wave_length - 755) / (755 - 580)
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
        if wave_length < 500:
            factor = 0.3 + 0.7*(wave_length - 380) / (500 - 380)
        elif wave_length < 751:
            factor = 1.0
        elif wave_length < 781:
            factor = 0.3 + 0.7*(780 - wave_length) / (780 - 750)
    else:
        factor = 0.0

    # Don't want 0^x = 1 for x <> 0
    rgb = (0 if red == 0 else int((intensity_max * ((red * factor) ** gamma)) // 1),
           0 if green == 0 else int((intensity_max * ((green * factor) ** gamma)) // 1),
           0 if blue == 0 else int((intensity_max * ((blue * factor) ** gamma)) // 1))

    return rgb

# real tries number = tries squared
tries = 1


def create_matrix(game_count=10):
    size = 100 * tries
    matrix = [[_ for _ in range(size)] for _ in range(size)]

    for i in range(size):
        for j in range(size):
           #matrix[i][j] = BigPig.get_winrate(10000, 10000, game_count, i/tries, 15-j/tries)
           # points vs. points
           # matrix[i][j] = BigPig.get_winrate(i/tries, 100-j/tries, game_count)
            matrix[i][j] = i
        if i % 10 == 0:
            print("done {} out of {}".format(i, size))
    return matrix


def generate_heatmap(data_matrix):

    size = 100 * tries
    min_freq = 1
    max_freq = 0

    def draw_heatmap_rgb(dataset):
        tile_size = 1
        im = Image.new("RGB", (size*tile_size, size*tile_size))
        draw = ImageDraw.Draw(im)
        for d in dataset:
            draw.rectangle([(d[0]*tile_size, d[1]*tile_size), ((d[0]+1)*tile_size, (d[1]+1)*tile_size)],
                           wave_length_to_rgb(get_wave_lenght_from_data_point(d[2], min_freq, max_freq)))
        # write file
        im.save("winrateGuideFromZeroToHundred.jpg", "PNG")

    data = []

    for i in range(size):
        for j in range(size):
            value = data_matrix[i][j]
            if value > max_freq:
                max_freq = value
            if value < min_freq:
                min_freq = value

    for i in range(size):
        for j in range(size):
            data.append((i, j, data_matrix[i][j]))

    draw_heatmap_rgb(data)


def main():
    generate_heatmap(create_matrix(1000))

if __name__ == "__main__":
    main()