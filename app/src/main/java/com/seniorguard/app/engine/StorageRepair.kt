package com.seniorguard.app.engine
import android.content.Context
import android.os.Environment
import java.io.File
class StorageRepair(private val context: Context) : RepairModule {
    override suspend fun fixAll(): Int { var c=0; if(cleanCache())c++; if(cleanTempFiles())c++; return c }
    fun cleanCache(): Boolean { var cleaned=false; try{ context.cacheDir?.let{ it.listFiles()?.forEach{f->f.deleteRecursively()}; cleaned=true }; context.externalCacheDir?.let{ it.listFiles()?.forEach{f->f.deleteRecursively()}; cleaned=true } } catch(e:Exception){}; return cleaned }
    fun cleanTempFiles(): Boolean { var cleaned=false; val d=File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path); if(d.exists())d.listFiles()?.forEach{ if(it.name.endsWith(".tmp")){ it.delete(); cleaned=true } }; return cleaned }
}
