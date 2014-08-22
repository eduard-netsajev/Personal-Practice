__author__ = 'Netšajev'


from PIL import Image, ImageDraw
import math


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


def generate_fractal():

    # on my laptop:
    # s5000 max_iter35 = 7280 sec, 17.3 MB
    # s10000 max_iter35 = 27350 sec, 47.8 MB

    size = 10000
    max_iteration = 35
    aliasing = True

    min_x = -2
    max_x = 1.0

    min_y = -1.25
    max_y = 1.25

    x_size = (max_x - min_x) * size
    y_size = (max_y - min_y) * size

    x_a, x_b = x_size / (max_x - min_x), min_x
    y_a, y_b = y_size / (max_y - min_y), min_y

    x_size = int(x_size)
    y_size = int(y_size)

    def get_scaled_x(pixel_x):
        result = pixel_x / x_a + x_b
        return result

    def get_scaled_y(pixel_y):
        result = pixel_y / y_a + y_b
        return -result

    def next_zet(complex_number, c):
        x, y = complex_number[0], complex_number[1]
        return x**2 - y**2 + c[0], 2*x*y + c[1]

    def complex_num_modulus(c_num):
        return c_num[0] ** 2 + c_num[1] ** 2

    def num_not_escaped(c_num):
        return not complex_num_modulus(c_num) > 4

    def get_iterations(x, y):
        c_num = [0.0, 0.0]
        c = [get_scaled_x(x), get_scaled_y(y)]
        iteration = 0
        while num_not_escaped(c_num) and iteration < max_iteration:
            c_num = next_zet(c_num, c)
            iteration += 1
        # http://en.wikipedia.org/wiki/Mandelbrot_set#Continuous_.28smooth.29_coloring
        if iteration < max_iteration and aliasing:
            zn = complex_num_modulus(c_num)
            nu = math.log(0.5 * math.log(zn) / math.log(2)) / math.log(2)
            iteration = iteration + 1 - nu
        return iteration

    def draw_heatmap_rgb():
        im = Image.new("RGB", (x_size, y_size))
        draw = ImageDraw.Draw(im)

        for y in range(0, y_size):
            for x in range(0, x_size):
                iterations = get_iterations(x, y)
                if iterations == max_iteration:
                    draw.rectangle([(x, y), ((x+1), (y+1))],
                                   (0, 0, 0))
                else:
                    draw.rectangle([(x, y), ((x+1), (y+1))],
                                   wave_length_to_rgb(get_wave_lenght_from_data_point(iterations, 0, max_iteration)))
            if y % 10 == 0:
                print("Done {}/{}".format(y, y_size))
        # write file
        im.save("fractal_s%s_c%s.png" % (size, max_iteration), "PNG")

    draw_heatmap_rgb()


def main():
    import time
    t0 = time.time()
    generate_fractal()
    t1 = time.time()
    exec_time = (t1-t0)*100//1/100

    print("It took {} seconds or {} min {} sec to generate the fractal ".format(exec_time, int(exec_time//60),
                                                                                int(exec_time % 60//1)))
    input()
    
if __name__ == '__main__':
    main()
