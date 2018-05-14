package info.upump.jym.entity;

import info.upump.jym.R;


public enum Day {
    MONDAY(){
        @Override
        public int getColor() {
            return R.color.monday ;
        }

        @Override
        public int getName() {
            return R.string.day_monday;
        }
    },
    TUESDAY(){
        @Override
        public int getColor() {
            return R.color.tuesday ;
        }

        @Override
        public int getName() {
            return R.string.day_tuesday;
        }
    },
    WEDNESDAY (){
        @Override
        public int getColor() {
            return R.color.wednesday ;
        }

        @Override
        public int getName() {
            return R.string.day_wednesday;
        }
    },
    THURSDAY(){
        @Override
        public int getColor() {
            return R.color.thursday;
        }

        @Override
        public int getName() {
            return R.string.day_thursday;
        }
    },
    FRIDAY(){
        @Override
        public int getColor() {
            return R.color.friday ;
        }

        @Override
        public int getName() {
            return R.string.day_friday;
        }
    },
    SATURDAY(){
        @Override
        public int getColor() {
            return R.color.saturday ;
        }

        @Override
        public int getName() {
            return R.string.day_saturday;
        }
    },
    SUNDAY(){
        @Override
        public int getColor() {
            return R.color.sunday;
        }

        @Override
        public int getName() {
            return R.string.day_sunday;
        }
    };

    public abstract int getColor();
    public abstract int getName();
}
