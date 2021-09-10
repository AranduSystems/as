/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

/**
 *
 * @author armando
 */
public class numero_letra {

    private static final int Unidad = 1;
    private static final int Decena = 10;
    private static final int Centena = 100;

    public static String readNumber(String Number, String sepDecimal, String sMoney) {

        String V[] = initVector(), s = "", z = "", c = "", e = " ", t;
        int l = Number.length(), k = Number.indexOf(sepDecimal), u = 1, n = 0, j = 0, b = 0, d, p, r;

        try {
            //obtiene los decimales
            if (k >= 0) {
                c = Number.substring(k + 1, l);
                l = k;
            }

            if (l <= 15) {
                for (int i = l; i >= 1; i--) {
                    d = Integer.parseInt(String.valueOf(Number.charAt(i - 1)));
                    n = (d * u) + n;

                    switch (u) {
                        case Unidad:
                            s = V[n];
                            if (i == l && n == 1) {
                                b++;
                            }
                            break;
                        case Decena:
                            p = d - 2;

                            if (p < 0) {
                                s = V[n];
                            } else {
                                t = V[20 + p];

                                if (n % 10 != 0) {
                                    s = (d == 2) ? "veinti" + s : t + " y " + s;
                                } else {
                                    s = t;
                                }
                            }
                            break;
                        case Centena:
                            p = d - 1;
                            t = V[30 + p];

                            if (n % 100 == 0) {
                                s = "";
                                e = "";
                            } else if (d == 1) {
                                t += "to";
                            }

                            s = t + e + s;
                            z = (s + z);
                            break;
                    }

                    e = " ";
                    //ini. calcula los miles, millones, billones
                    r = l - i;
                    if (r > 0 && r % 3 == 0) {
                        p = (r > 2) ? 2 : j++ & 1;
                        t = V[40 + p];

                        if (p > 0) {
                            if ((n == 1 && i > 1) || n > 1) {
                                t += "es";
                            }
                        }

                        z = e + t + e + z;
                    }
                    //fin.

                    //reinicia las variables
                    if (u == Centena) {
                        u = 1;
                        n = 0;
                        s = "";
                    } else {
                        u *= 10;
                    }
                }

            }

            //ini. adiciona la moneda y los centavos
            if (!c.equals("")) {
                c = " con " + c + " centavos";
            }
            if (!sMoney.equals("")) {
                sMoney = " " + sMoney;
            } else if (b > 0) {
                z += "o";
            }
            //fin.

            z = (s + z) + sMoney + c;
        } catch (NumberFormatException ex) {
            z = "ERROR [readNumber]: Formato numerico incorrecto.";
        }

        return z;

    }

    private static String[] initVector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
