package info.upump.jym.entity;

import info.upump.jym.R;


public enum TypeMuscle {

    NECK() {
       int img = R.drawable.ic_menu_camera;
       int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    SHOULDER() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    PECTORAL() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },

    TRAPS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    CHEST() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    TRICEPS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
     BICEPS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    BACK() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    GLUTES() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    QUADS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    HAMSTRINGS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    CALVES() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    },
    ABS() {
        int img = R.drawable.ic_menu_camera;
        int name = R.string.biceps;
        @Override
        public int getImg() {
            return img;
        }

        @Override
        public int getName() {
            return name;
        }
    };

    public abstract int getImg();
    public abstract int getName();




  /*  BICEPS(R.drawable.ic_menu_camera) {
        int nane = R.string.biceps;

        @Override
        public int getName() {
            return nane;
        }
    };
    int resourceImg;

    TypeMuscle(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public abstract int getName();*/
}
