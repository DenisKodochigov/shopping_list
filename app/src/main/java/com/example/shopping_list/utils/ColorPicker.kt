package com.example.shopping_list.utils

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable fun ColorPicker() {
    var colorIndex by remember { mutableStateOf(0)}
    var colorPosition by remember{mutableStateOf(0f)}
    var alfaPosition by remember{mutableStateOf(0f)}

//    val listColor: List<String> = ColorList.list2.map { it.rgb() }
    val listColor: List<Long> = createListSpectrumRGB()
    val spectrum: List<Color> = listColor.map { Color(it) }
    Column( modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            var color = listColor[colorIndex]
            Box( modifier = Modifier
                .background(color = Color(color))
                .width(50.dp)
                .height(50.dp)
                .padding(horizontal = 32.dp))
            color = listColor[colorIndex] - 4278190080 + 16777216 * alfaPosition.roundToInt()
            Box( modifier = Modifier
                .background(color = Color(color))
                .width(50.dp)
                .height(50.dp)
                .padding(horizontal = 32.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box( modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(brush = Brush.horizontalGradient(colors = spectrum)))

        Text(text = "Цвет", style = MaterialTheme.typography.h4)
        Slider(
            value = colorPosition,
            valueRange = 0f..listColor.size.toFloat()-1,
            steps = listColor.size,
            onValueChange = {
                colorPosition = it
                colorIndex = it.roundToInt() },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF575757),
                activeTrackColor = Color(0xFFA2A2A2),
                inactiveTrackColor = Color(0xFFA2A2A2),
                inactiveTickColor = Color(0xFFA2A2A2),
                activeTickColor = Color(0xFF575757)
        ))
        Text(text = "Прозрачность", style = MaterialTheme.typography.h4)
        Slider(
            value = alfaPosition,
            valueRange = 0f..255f,
            steps = listColor.size,
            onValueChange = {
                alfaPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF575757),
                activeTrackColor = Color(0xFFA2A2A2),
                inactiveTrackColor = Color(0xFFA2A2A2),
                inactiveTickColor = Color(0xFFA2A2A2),
                activeTickColor = Color(0xFF575757)
            ))
    }
}

@Composable fun ColorPicker1(){

    val colors = mutableListOf<Int>()
    for (i in 0..16777215 step 10000) colors.add(i)

    Box(modifier = Modifier
        .height(320.dp)
        .fillMaxWidth()
        .background(color = Color.LightGray)) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 32.dp),
            modifier = Modifier,
            state = rememberLazyGridState(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(0.dp),
        )
        {
            items(colors.size)
            { index ->
                val item = colors[index].toInt()
                Box(modifier = Modifier
                    .background(shape = CircleShape, color = Color(4278190080 + item))
                    .size(32.dp))
            }
        }
    }
}

@Composable fun ColorPicker2(){
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()
}

fun createListSpectrumRGB(): List<Long>{
    val listColor = mutableListOf<Long>()
    for (wave in 380..780 step 2) {
        listColor.add(waveToRGB(wave))
    }
    return listColor
}


fun waveToRGB(wavelength: Int): Long{

    val factor = if((wavelength >= 380) && (wavelength<420)) 0.3 + 0.7*(wavelength - 380) / (420 - 380)
        else if((wavelength >= 420) && (wavelength<701)) 1.0
        else if((wavelength >= 701) && (wavelength<781)) 0.3 + 0.7*(780 - wavelength) / (780 - 700)
        else 0.0

    return if((wavelength >= 380) && (wavelength<440)){
        calculateColor((-(wavelength - 440) / (440 - 380.0)), 0.0, 1.0, factor)
    } else if((wavelength >= 440) && (wavelength<490)){
        calculateColor(0.0, (wavelength - 440) / (490 - 440.0), 1.0, factor)
    }else if((wavelength >= 490) && (wavelength<510)){
        calculateColor(0.0, 1.0, (-(wavelength - 510) / (510 - 490.0)), factor)
    }else if((wavelength >= 510) && (wavelength<580)){
        calculateColor((wavelength - 510) / (580 - 510.0), 1.0, 0.0, factor)
    }else if((wavelength >= 580) && (wavelength<645)){
        calculateColor(1.0, (-(wavelength - 645) / (645 - 580.0)), 0.0, factor)
    }else if((wavelength >= 645) && (wavelength<781)){
        calculateColor(1.0, 0.0, 0.0, factor)
    } else calculateColor(0.0, 0.0, 0.0, factor)

}

fun calculateColor(red: Double, green: Double, blue: Double, factor: Double): Long {
    val gamma = 0.80
    val intensityMax = 255

    var rgbHex = 4278190080
    rgbHex += if (red != 0.0) (intensityMax * (red * factor).pow(gamma)).roundToInt() * 65536 else 0
    rgbHex += if (green != 0.0) (intensityMax * (green * factor).pow(gamma)).roundToInt() * 256 else 0
    rgbHex += if (blue != 0.0) (intensityMax * (blue * factor).pow(gamma)).roundToInt() else 0

    return rgbHex
}
/*
Если интересует именно "как бы максимум" - перейти от RGB (sRGB?) к xy (не путать с XYZ!)
и найти на спектральной кривой точку, лежащую на одной прямой с заданной и белой точками.
Далее - тривиально (частоты точек на спектральной кривой известны).
Напомню, что переход на плоскость (x,y) путём избавления от яркости делается так:
x=X/(X+Y+Z);
y=Y/(X+Y+Z);

Вот, выкопал в загашнике достаточно точную аппроксимацию спектральной кривой (в пространстве XYZ):
Дл.волны (нм)        X      Y      Z
    380          0.1741 0.0050 0.8209
    385          0.1740 0.0050 0.8210
    390          0.1738 0.0049 0.8213
    395          0.1736 0.0049 0.8215
    400          0.1733 0.0048 0.8219
    405          0.1730 0.0048 0.8222
    410          0.1726 0.0048 0.8226
    415          0.1721 0.0048 0.8231
    420          0.1714 0.0051 0.8235
    425          0.1703 0.0058 0.8239
    430          0.1689 0.0069 0.8242
    435          0.1669 0.0086 0.8245
    440          0.1644 0.0109 0.8247
    445          0.1611 0.0138 0.8251
    450          0.1566 0.0177 0.8257
    455          0.1510 0.0227 0.8263
    460          0.1440 0.0297 0.8263
    465          0.1355 0.0399 0.8246
    470          0.1241 0.0578 0.8181
    475          0.1096 0.0868 0.8036
    480          0.0913 0.1327 0.7760
    485          0.0687 0.2007 0.7306
    490          0.0454 0.2950 0.6596
    495          0.0235 0.4127 0.5638
    500          0.0082 0.5384 0.4534
    505          0.0039 0.6548 0.3413
    510          0.0139 0.7502 0.2359
    515          0.0389 0.8120 0.1491
    520          0.0743 0.8338 0.0919
    525          0.1142 0.8262 0.0596
    530          0.1547 0.8059 0.0394
    535          0.1929 0.7816 0.0255
    540          0.2296 0.7543 0.0161
    545          0.2658 0.7243 0.0099
    550          0.3016 0.6923 0.0061
    555          0.3373 0.6589 0.0038
    560          0.3731 0.6245 0.0024
    565          0.4087 0.5896 0.0017
    570          0.4441 0.5547 0.0012
    575          0.4788 0.5202 0.0010
    580          0.5125 0.4866 0.0009
    585          0.5448 0.4544 0.0008
    590          0.5752 0.4242 0.0006
    595          0.6029 0.3965 0.0006
    600          0.6270 0.3725 0.0005
    605          0.6482 0.3514 0.0004
    610          0.6658 0.3340 0.0002
    615          0.6801 0.3197 0.0002
    620          0.6915 0.3083 0.0002
    625          0.7006 0.2993 0.0001
    630          0.7079 0.2920 0.0001
    635          0.7140 0.2859 0.0001
    640          0.7190 0.2809 0.0001
    645          0.7230 0.2770 0.0000
    650          0.7260 0.2740 0.0000
    655          0.7283 0.2717 0.0000
    660          0.7300 0.2700 0.0000
    665          0.7311 0.2689 0.0000
    670          0.7320 0.2680 0.0000
    675          0.7327 0.2673 0.0000
    680          0.7334 0.2666 0.0000
    685          0.7340 0.2660 0.0000
    690          0.7344 0.2656 0.0000
    695          0.7346 0.2654 0.0000
    700          0.7347 0.2653 0.0000
    705          0.7347 0.2653 0.0000
    710          0.7347 0.2653 0.0000
    715          0.7347 0.2653 0.0000
    720          0.7347 0.2653 0.0000
    725          0.7347 0.2653 0.0000
    730          0.7347 0.2653 0.0000
    735          0.7347 0.2653 0.0000
    740          0.7347 0.2653 0.0000
    745          0.7347 0.2653 0.0000
    750          0.7347 0.2653 0.0000
    755          0.7347 0.2653 0.0000
    760          0.7347 0.2653 0.0000
    765          0.7347 0.2653 0.0000
    770          0.7347 0.2653 0.0000
    775          0.7347 0.2653 0.0000
    780          0.7347 0.2653 0.0000
Напомню, что переход на плоскость (x,y) путём избавления от яркости делается так:
x=X/(X+Y+Z);
y=Y/(X+Y+Z);
Дл.волны (нм) 	X 	Y 	Z	  x	       y
380	0,1741	0,0050	0,8209	0,1741	0,0050
385 0,1740	0,0050	0,8210	0,1740	0,0050
390	0,1738	0,0049	0,8213	0,1738	0,0049
395	0,1736	0,0049	0,8215	0,1736	0,0049
400	0,1733	0,0048	0,8219	0,1733	0,0048
405	0,1730	0,0048	0,8222	0,1730	0,0048
410	0,1726	0,0048	0,8226	0,1726	0,0048
415	0,1721	0,0048	0,8231	0,1721	0,0048
420	0,1714	0,0051	0,8235	0,1714	0,0051
425	0,1703	0,0058	0,8239	0,1703	0,0058
430	0,1689	0,0069	0,8242	0,1689	0,0069
435	0,1669	0,0086	0,8245	0,1669	0,0086
440	0,1644	0,0109	0,8247	0,1644	0,0109
445	0,1611	0,0138	0,8251	0,1611	0,0138
450	0,1566	0,0177	0,8257	0,1566	0,0177
455	0,1510	0,0227	0,8263	0,1510	0,0227
460	0,1440	0,0297	0,8263	0,1440	0,0297
465	0,1355	0,0399	0,8246	0,1355	0,0399
470	0,1241	0,0578	0,8181	0,1241	0,0578
475	0,1096	0,0868	0,8036	0,1096	0,0868
480	0,0913	0,1327	0,7760	0,0913	0,1327
485	0,0687	0,2007	0,7306	0,0687	0,2007
490	0,0454	0,2950	0,6596	0,0454	0,2950
495	0,0235	0,4127	0,5638	0,0235	0,4127
500	0,0082	0,5384	0,4534	0,0082	0,5384
505	0,0039	0,6548	0,3413	0,0039	0,6548
510	0,0139	0,7502	0,2359	0,0139	0,7502
515	0,0389	0,8120	0,1491	0,0389	0,8120
520	0,0743	0,8338	0,0919	0,0743	0,8338
525	0,1142	0,8262	0,0596	0,1142	0,8262
530	0,1547	0,8059	0,0394	0,1547	0,8059
535	0,1929	0,7816	0,0255	0,1929	0,7816
540	0,2296	0,7543	0,0161	0,2296	0,7543
545	0,2658	0,7243	0,0099	0,2658	0,7243
550	0,3016	0,6923	0,0061	0,3016	0,6923
555	0,3373	0,6589	0,0038	0,3373	0,6589
560	0,3731	0,6245	0,0024	0,3731	0,6245
565	0,4087	0,5896	0,0017	0,4087	0,5896
570	0,4441	0,5547	0,0012	0,4441	0,5547
575	0,4788	0,5202	0,0010	0,4788	0,5202
580	0,5125	0,4866	0,0009	0,5125	0,4866
585	0,5448	0,4544	0,0008	0,5448	0,4544
590	0,5752	0,4242	0,0006	0,5752	0,4242
595	0,6029	0,3965	0,0006	0,6029	0,3965
600	0,6270	0,3725	0,0005	0,6270	0,3725
605	0,6482	0,3514	0,0004	0,6482	0,3514
610	0,6658	0,3340	0,0002	0,6658	0,3340
615	0,6801	0,3197	0,0002	0,6801	0,3197
620	0,6915	0,3083	0,0002	0,6915	0,3083
625	0,7006	0,2993	0,0001	0,7006	0,2993
630	0,7079	0,2920	0,0001	0,7079	0,2920
635	0,7140	0,2859	0,0001	0,7140	0,2859
640	0,7190	0,2809	0,0001	0,7190	0,2809
645	0,7230	0,2770	0,0000	0,7230	0,2770
650	0,7260	0,2740	0,0000	0,7260	0,2740
655	0,7283	0,2717	0,0000	0,7283	0,2717
660	0,7300	0,2700	0,0000	0,7300	0,2700
665	0,7311	0,2689	0,0000	0,7311	0,2689
670	0,7320	0,2680	0,0000	0,7320	0,2680
675	0,7327	0,2673	0,0000	0,7327	0,2673
680	0,7334	0,2666	0,0000	0,7334	0,2666
685	0,7340	0,2660	0,0000	0,7340	0,2660
690	0,7344	0,2656	0,0000	0,7344	0,2656
695	0,7346	0,2654	0,0000	0,7346	0,2654
700	0,7347	0,2653	0,0000	0,7347	0,2653
705	0,7347	0,2653	0,0000	0,7347	0,2653
710	0,7347	0,2653	0,0000	0,7347	0,2653
715	0,7347	0,2653	0,0000	0,7347	0,2653
720	0,7347	0,2653	0,0000	0,7347	0,2653
725	0,7347	0,2653	0,0000	0,7347	0,2653
730	0,7347	0,2653	0,0000	0,7347	0,2653
735	0,7347	0,2653	0,0000	0,7347	0,2653
740	0,7347	0,2653	0,0000	0,7347	0,2653
745	0,7347	0,2653	0,0000	0,7347	0,2653
750	0,7347	0,2653	0,0000	0,7347	0,2653
755	0,7347	0,2653	0,0000	0,7347	0,2653
760	0,7347	0,2653	0,0000	0,7347	0,2653
765	0,7347	0,2653	0,0000	0,7347	0,2653
770	0,7347	0,2653	0,0000	0,7347	0,2653
775	0,7347	0,2653	0,0000	0,7347	0,2653
780	0,7347	0,2653	0,0000	0,7347	0,2653

R =  3.2404542*X - 1.5371385*Y - 0.4985314*Z
G = -0.9692660*X + 1.8760108*Y + 0.0415560*Z
B =  0.0556434*X - 0.2040259*Y + 1.0572252*Z
Однако, если вы имели в виду пространство sRGB, то к каждому компоненту необходимо применить
дополнительное нелинейное преобразование: R=adj(R), G=adj(G) и B=adj(B). adj
Функция определяется следующим образом:
function adj(C) {
  if (Abs(C) < 0.0031308) {
    return 12.92 * C;
  }
  return 1.055 * Math.pow(C, 0.41666) - 0.055;
}

1.0144665  0.0000000  0.0000000
 0.0000000  1.0000000  0.0000000
 0.0000000  0.0000000  0.7578869

Colour matching functions
nm	X	Y	Z
390	3.769647E-03	4.146161E-04	1.847260E-02
395	9.382967E-03	1.059646E-03	4.609784E-02
400	2.214302E-02	2.452194E-03	1.096090E-01
405	4.742986E-02	4.971717E-03	2.369246E-01
410	8.953803E-02	9.079860E-03	4.508369E-01
415	1.446214E-01	1.429377E-02	7.378822E-01
420	2.035729E-01	2.027369E-02	1.051821E+00
425	2.488523E-01	2.612106E-02	1.305008E+00
430	2.918246E-01	3.319038E-02	1.552826E+00
435	3.227087E-01	4.157940E-02	1.748280E+00
440	3.482554E-01	5.033657E-02	1.917479E+00
445	3.418483E-01	5.743393E-02	1.918437E+00
450	3.224637E-01	6.472352E-02	1.848545E+00
455	2.826646E-01	7.238339E-02	1.664439E+00
460	2.485254E-01	8.514816E-02	1.522157E+00
465	2.219781E-01	1.060145E-01	1.428440E+00
470	1.806905E-01	1.298957E-01	1.250610E+00
475	1.291920E-01	1.535066E-01	9.991789E-01
480	8.182895E-02	1.788048E-01	7.552379E-01
485	4.600865E-02	2.064828E-01	5.617313E-01
490	2.083981E-02	2.379160E-01	4.099313E-01
495	7.097731E-03	2.850680E-01	3.105939E-01
500	2.461588E-03	3.483536E-01	2.376753E-01
505	3.649178E-03	4.277595E-01	1.720018E-01
510	1.556989E-02	5.204972E-01	1.176796E-01
515	4.315171E-02	6.206256E-01	8.283548E-02
520	7.962917E-02	7.180890E-01	5.650407E-02
525	1.268468E-01	7.946448E-01	3.751912E-02
530	1.818026E-01	8.575799E-01	2.438164E-02
535	2.405015E-01	9.071347E-01	1.566174E-02
540	3.098117E-01	9.544675E-01	9.846470E-03
545	3.804244E-01	9.814106E-01	6.131421E-03
550	4.494206E-01	9.890228E-01	3.790291E-03
555	5.280233E-01	9.994608E-01	2.327186E-03
560	6.133784E-01	9.967737E-01	1.432128E-03
565	7.016774E-01	9.902549E-01	8.822531E-04
570	7.967750E-01	9.732611E-01	5.452416E-04
575	8.853376E-01	9.424569E-01	3.386739E-04
580	9.638388E-01	8.963613E-01	2.117772E-04
585	1.051011E+00	8.587203E-01	1.335031E-04
590	1.109767E+00	8.115868E-01	8.494468E-05
595	1.143620E+00	7.544785E-01	5.460706E-05
600	1.151033E+00	6.918553E-01	3.549661E-05
605	1.134757E+00	6.270066E-01	2.334738E-05
610	1.083928E+00	5.583746E-01	1.554631E-05
615	1.007344E+00	4.895950E-01	1.048387E-05
620	9.142877E-01	4.229897E-01	0.000000E+00
625	8.135565E-01	3.609245E-01	0.000000E+00
630	6.924717E-01	2.980865E-01	0.000000E+00
635	5.755410E-01	2.416902E-01	0.000000E+00
640	4.731224E-01	1.943124E-01	0.000000E+00
645	3.844986E-01	1.547397E-01	0.000000E+00
650	2.997374E-01	1.193120E-01	0.000000E+00
655	2.277792E-01	8.979594E-02	0.000000E+00
660	1.707914E-01	6.671045E-02	0.000000E+00
665	1.263808E-01	4.899699E-02	0.000000E+00
670	9.224597E-02	3.559982E-02	0.000000E+00
675	6.639960E-02	2.554223E-02	0.000000E+00
680	4.710606E-02	1.807939E-02	0.000000E+00
685	3.292138E-02	1.261573E-02	0.000000E+00
690	2.262306E-02	8.661284E-03	0.000000E+00
695	1.575417E-02	6.027677E-03	0.000000E+00
700	1.096778E-02	4.195941E-03	0.000000E+00
705	7.608750E-03	2.910864E-03	0.000000E+00
710	5.214608E-03	1.995557E-03	0.000000E+00
715	3.569452E-03	1.367022E-03	0.000000E+00
720	2.464821E-03	9.447269E-04	0.000000E+00
725	1.703876E-03	6.537050E-04	0.000000E+00
730	1.186238E-03	4.555970E-04	0.000000E+00
735	8.269535E-04	3.179738E-04	0.000000E+00
740	5.758303E-04	2.217445E-04	0.000000E+00
745	4.058303E-04	1.565566E-04	0.000000E+00
750	2.856577E-04	1.103928E-04	0.000000E+00
755	2.021853E-04	7.827442E-05	0.000000E+00
760	1.438270E-04	5.578862E-05	0.000000E+00
765	1.024685E-04	3.981884E-05	0.000000E+00
770	7.347551E-05	2.860175E-05	0.000000E+00
775	5.259870E-05	2.051259E-05	0.000000E+00
780	3.806114E-05	1.487243E-05	0.000000E+00
785	2.758222E-05	1.080001E-05	0.000000E+00
790	2.004122E-05	7.863920E-06	0.000000E+00
795	1.458792E-05	5.736935E-06	0.000000E+00
800	1.068141E-05	4.211597E-06	0.000000E+00
805	7.857521E-06	3.106561E-06	0.000000E+00
810	5.768284E-06	2.286786E-06	0.000000E+00
815	4.259166E-06	1.693147E-06	0.000000E+00
820	3.167765E-06	1.262556E-06	0.000000E+00
825	2.358723E-06	9.422514E-07	0.000000E+00
830	1.762465E-06	7.053860E-07	0.000000E+00

 */