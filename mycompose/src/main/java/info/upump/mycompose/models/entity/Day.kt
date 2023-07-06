package info.upump.mycompose.models.entity

import info.upump.mycompose.R


enum class Day {
    MONDAY {
        override fun getColor(): Int {
            return R.color.monday
        }

        override fun getName(): Int {
            return R.string.day_monday
        }
    },
    TUESDAY {
        override fun getColor(): Int {
            return R.color.tuesday
        }

        override fun getName(): Int {
            return R.string.day_tuesday
        }
    },
    WEDNESDAY {
        override fun getColor(): Int {
            return R.color.wednesday
        }

        override fun getName(): Int {
            return R.string.day_wednesday
        }
    },
    THURSDAY {
        override fun getColor(): Int {
            return R.color.thursday
        }

        override fun getName(): Int {
            return R.string.day_thursday
        }
    },
    FRIDAY {
        override fun getColor(): Int {
            return R.color.friday
        }

        override fun getName(): Int {
            return R.string.day_friday
        }
    },
    SATURDAY {
        override fun getColor(): Int {
            return R.color.saturday
        }

        override fun getName(): Int {
            return R.string.day_saturday
        }
    },
    SUNDAY {
        override fun getColor(): Int {
            return R.color.sunday
        }

        override fun getName(): Int {
            return R.string.day_sunday
        }
    };

    abstract fun getColor(): Int
    abstract fun getName(): Int
}