package com.example.demo;

    public class Word {
        /**
         * target để lưu từ tiếng anh
         */
        private String target;
        /**
         * explain để lưu nghĩa và phiên âm
         */
        private String explain;
        /**
         * trả về từ tiếng anh của từ
         *
         * @return target
         */
        public String getTarget() {
            return target;
        }

        /**
         * trả về nghĩa và phiên âm của từ
         *
         * @return explain
         */

        public String getExplain() {
            return explain;
        }

        /**
         * cài đặt nghĩa tiếng anh của từ
         *
         * @param target lưu từ tiếng anh
         */
        public void setTarget(String target) {
            this.target = target;
        }

        /**
         * cài đặt từ tiếng anh của từ
         *
         * @param explain lưu nghĩa và phiên âm của từ
         */
        public void setExplain(String explain) {
            this.explain = explain;
        }

        /**
         * constructer 2 tham số
         *
         * @param target lưu từ tiếng anh
         * @param explain lưu nghĩa và phiên âm của từ
         */
        public Word(String target, String explain) {
            this.explain = explain;
            this.target = target;
        }

        /**
         * trả về chuỗi theo dạng từ + phiên âm + nghĩa
         *
         * @return string
         */
        @Override
        public String toString(){return target + " " + explain;}
    }

