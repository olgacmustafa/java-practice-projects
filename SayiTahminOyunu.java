
/**
 * YMÜ227 - Nesne Tabanlı Programlama
 * Sayı Tahmin Oyunu
 * Öğrenci: Muhyeddin Olğaç
 * Öğrenci No: 240290008
 * Teslim Tarihi: 26.10.2025
 */


package com.mycompany.SayiTahminOyunu;

import java.util.*;

public class SayiTahminOyunu {

    // ==== Ayar Sınıfı ====
    static class OyunAyar {
        int min;
        int max;
        int maxDeneme;
        int sureLimitSaniye;

        OyunAyar(int min, int max, int maxDeneme, int sureLimitSaniye) {
            this.min = min;
            this.max = max;
            this.maxDeneme = maxDeneme;
            this.sureLimitSaniye = sureLimitSaniye;
        }

        boolean sureKisitliMi() {
            return sureLimitSaniye > 0;
        }
    }

    // ==== Rastgele Sayı Üretici ====
    static class RastgeleSayiUretici {
        int min, max;

        RastgeleSayiUretici(int min, int max) {
            this.min = min;
            this.max = max;
        }

        int uret() {
            return new Random().nextInt((max - min) + 1) + min;
        }
    }

    // ==== Geri Sayım (Opsiyonel Süre Kısıtı) ====
    static class GeriSayim {
        int toplamSaniye;
        long baslangicMillis;

        GeriSayim(int toplamSaniye) {
            this.toplamSaniye = toplamSaniye;
        }

        void baslat() {
            baslangicMillis = System.currentTimeMillis();
        }

        int kalanSaniye() {
            if (baslangicMillis == 0) return toplamSaniye;
            long gecen = (System.currentTimeMillis() - baslangicMillis) / 1000;
            return (int) Math.max(0, toplamSaniye - gecen);
        }

        boolean suresiBittiMi() {
            return kalanSaniye() <= 0;
        }
    }

    // ==== Deneme Sınıfı ====
    static class Deneme {
        enum Sonuc { YUKSEK, DUSUK, DOGRU }

        int tahminDegeri;
        Sonuc sonuc;
        int denemeNo;

        Deneme(int tahminDegeri, Sonuc sonuc, int denemeNo) {
            this.tahminDegeri = tahminDegeri;
            this.sonuc = sonuc;
            this.denemeNo = denemeNo;
        }

        public String toString() {
            return denemeNo + ". deneme: " + tahminDegeri + " -> " + sonuc;
        }
    }

    // ==== Oyun Mekaniği ====
    static class Oyun {
        OyunAyar ayar;
        int gizliSayi;
        int kalanDeneme;
        List<Deneme> denemeler = new ArrayList<>();
        GeriSayim geriSayim;
        boolean bitti = false;
        boolean kazandi = false;

        Oyun(OyunAyar ayar) {
            this.ayar = ayar;
            RastgeleSayiUretici uretici = new RastgeleSayiUretici(ayar.min, ayar.max);
            this.gizliSayi = uretici.uret();
            this.kalanDeneme = ayar.maxDeneme;
            if (ayar.sureKisitliMi()) {
                this.geriSayim = new GeriSayim(ayar.sureLimitSaniye);
                this.geriSayim.baslat();
            }
        }

        Deneme tahminiDegerlendir(int tahmin) {
            if (bitti) throw new IllegalStateException("Oyun zaten bitti.");

            Deneme.Sonuc sonuc;
            if (tahmin > gizliSayi) sonuc = Deneme.Sonuc.YUKSEK;
            else if (tahmin < gizliSayi) sonuc = Deneme.Sonuc.DUSUK;
            else {
                sonuc = Deneme.Sonuc.DOGRU;
                kazandi = true;
            }

            Deneme d = new Deneme(tahmin, sonuc, ayar.maxDeneme - kalanDeneme + 1);
            denemeler.add(d);

            if (!kazandi) kalanDeneme--;
            if (kazandi || kalanDeneme <= 0) bitti = true;

            return d;
        }

        boolean oyunBittiMi() {
            if (bitti) return true;
            if (geriSayim != null && geriSayim.suresiBittiMi()) {
                bitti = true;
            }
            return bitti;
        }

        boolean kazandiMi() {
            return kazandi;
        }

        int kalanSaniye() {
            return (geriSayim == null) ? -1 : geriSayim.kalanSaniye();
        }
    }

    // ==== Ana Program (main) ====
    public static void main(String[] args) {
        Scanner giris = new Scanner(System.in);
        System.out.println("=== Sayı Tahmin Oyunu ===");

        System.out.print("Aralık seçiniz (1) 1-100 (2) 1-1000 : ");
        int secim = okuAralikSecimi(giris, 1, 2, 1);
        int min = 1, max = (secim == 1 ? 100 : 1000);

        System.out.print("Deneme hakkı (varsayılan 7/10): ");
        int varsDeneme = (max == 100 ? 7 : 10);
        int maxDeneme = okuTamSayiVars(giris, 1, 100, varsDeneme);

        System.out.print("Süre kısıtı kullanılsın mı? (e/h): ");
        boolean sureVar = okuEvetHayir(giris, false);
        int toplamSaniye = 0;
        if (sureVar) {
            System.out.print("Toplam saniye (varsayılan 60): ");
            toplamSaniye = okuTamSayiVars(giris, 1, 600, 60);
        }

        OyunAyar ayar = new OyunAyar(min, max, maxDeneme, toplamSaniye);
        Oyun oyun = new Oyun(ayar);

        System.out.println("Oyun başladı! " + min + " ile " + max + " arasında bir sayı tuttum.");
        System.out.println("Toplam deneme hakkınız: " + maxDeneme);
        if (sureVar) System.out.println("Süre: " + toplamSaniye + " saniye.");

        while (!oyun.oyunBittiMi()) {
            if (oyun.kalanSaniye() == 0 && sureVar) break;

            System.out.print("Tahmininiz: ");
            String satir = giris.nextLine();
            if (satir.trim().isEmpty()) {
                System.out.println("Boş girdi, tekrar deneyin.");
                continue;
            }

            int tahmin;
            try {
                tahmin = Integer.parseInt(satir.trim());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir tam sayı girin.");
                continue;
            }

            if (tahmin < min || tahmin > max) {
                System.out.println("Aralık dışı değer!");
                continue;
            }

            Deneme d = oyun.tahminiDegerlendir(tahmin);
            switch (d.sonuc) {
                case YUKSEK -> System.out.println("Çok yüksek!");
                case DUSUK -> System.out.println("Çok düşük!");
                case DOGRU -> System.out.println("Tebrikler! Doğru tahmin!");
            }

            if (!oyun.oyunBittiMi()) {
                System.out.println("Kalan deneme: " + oyun.kalanDeneme +
                        (sureVar ? " | Kalan süre: " + oyun.kalanSaniye() + "sn" : ""));
            }
        }

        // Oyun Sonucu
        if (oyun.kazandiMi()) {
            System.out.println("Kazandınız! " + oyun.denemeler.size() + " denemede bildiniz!");
        } else if (sureVar && oyun.kalanSaniye() == 0) {
            System.out.println("Süre doldu! Kaybettiniz. Sayı: " + oyun.gizliSayi);
        } else {
            System.out.println("Deneme hakkınız bitti! Sayı: " + oyun.gizliSayi);
        }

        System.out.println("Deneme geçmişi:");
        for (Deneme d : oyun.denemeler) System.out.println(" - " + d);

        giris.close();
    }

    // ==== Yardımcı Metodlar ====
    static int okuAralikSecimi(Scanner s, int min, int max, int vars) {
        String satir = s.nextLine();
        if (satir.trim().isEmpty()) return vars;
        try {
            int deger = Integer.parseInt(satir);
            if (deger < min || deger > max) return vars;
            return deger;
        } catch (Exception e) {
            return vars;
        }
    }

    static int okuTamSayiVars(Scanner s, int min, int max, int vars) {
        String satir = s.nextLine();
        if (satir.trim().isEmpty()) return vars;
        try {
            int v = Integer.parseInt(satir);
            if (v < min || v > max) {
                System.out.println("Aralık dışı, varsayılan kullanıldı.");
                return vars;
            }
            return v;
        } catch (Exception e) {
            System.out.println("Geçersiz giriş, varsayılan kullanıldı.");
            return vars;
        }
    }

    static boolean okuEvetHayir(Scanner s, boolean vars) {
        String satir = s.nextLine();
        if (satir.trim().isEmpty()) return vars;
        char c = Character.toLowerCase(satir.charAt(0));
        return (c == 'e' || c == 'y');
    }
}
