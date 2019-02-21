package com.rifle.simple_diary.utils

import android.content.Context
import com.litesuits.orm.LiteOrm
import com.litesuits.orm.db.DataBaseConfig
import com.litesuits.orm.db.assit.QueryBuilder
import com.rifle.simple_diary.BuildConfig
import com.rifle.simple_diary.app.Constants
import com.rifle.simple_diary.model.ConfigBean

class DbUtils {


    companion object {
        private var liteOrm: LiteOrm? = null

        private fun ormInstance(context: Context): LiteOrm? {
            val mContext = context.applicationContext
            if (liteOrm == null) {
                synchronized(this) {
                    if (liteOrm == null) {
                        val config = DataBaseConfig(mContext, "diary.db")
                        config.debugged = BuildConfig.DEBUG
                        config.dbVersion = 1
                        config.onUpdateListener = null
                        liteOrm = LiteOrm.newCascadeInstance(config)
                    }
                }
            }
            return liteOrm
        }

        fun saveOrUpdateConfig(context: Context, config: ConfigBean) {
            val liteOrm = ormInstance(context)
            val qb = QueryBuilder(ConfigBean::class.java)
                    .whereEquals("key", config.key)
            val results = liteOrm?.query(qb)
            if (results != null && results.size > 0) {
                val oldConfig = results[0]
                config.value?.let { oldConfig?.value = config.value }
                oldConfig?.enabled = config.enabled
                liteOrm.save(oldConfig)
            } else {
                liteOrm?.save(config)
            }
        }


        fun getConfig(context: Context, key: String): String? {
            val liteOrm = ormInstance(context)
            val qb = QueryBuilder(ConfigBean::class.java)
                    .whereEquals(Constants.KEY, key)
                    .whereAppendAnd()
                    .whereEquals(Constants.VALUE, 1)
            val results = liteOrm?.query(qb)
            results?.let {
                if (results.size > 0) {
                    return it[0].value
                }
            }
            return null
        }


        fun closeDb() {
            liteOrm?.close()
        }
    }
}