package com.rifle.simple_diary.model

import com.litesuits.orm.db.annotation.*
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable

@Table("config")
class ConfigBean(key: String, value: String, enabled: Int) : Serializable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("id")
    var id: Int = 0

    @Column("key")
    @NotNull
    @Default("key")
    var key: String? = key

    @Column("value")
    @NotNull
    @Default("value")
    var value: String? = value

    // 1: enabled 0: disabled
    @Column("enable")
    @Default("1")
    var enabled: Int = 0

    init {
        this.key = key
        this.value = value
        this.enabled = enabled
    }
}