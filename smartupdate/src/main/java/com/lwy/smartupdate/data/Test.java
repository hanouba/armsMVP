package com.lwy.smartupdate.data;

/**
 * @author HanN on 2019/11/15 13:53
 * @email: 1356548475@qq.com
 * @project AppSmartUpdate-master
 * @description:
 * @updateuser:
 * @updatedata: 2019/11/15 13:53
 * @updateremark:
 * @version: 2.1.67
 */
public class Test {

    /**
     * minVersion : 100
     * minAllowPatchVersion : 100
     * newVersion : 101
     * tip : test update
     * apkURL : https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk
     * hash : ea97c8efa490a2eaf7d10b37e63dab0e
     * patchInfo : {"v100":{"patchURL":"v100/100to101.patch","tip":"101 version(本次更新包大小:1114810byte)","hash":"ea97c8efa490a2eaf7d10b37e63dab0e","size":1114810}}
     * size : 1956631
     */

    private int minVersion;
    private int minAllowPatchVersion;
    private int newVersion;
    private String tip;
    private String apkURL;
    private String hash;
    private PatchInfoBean patchInfo;
    private int size;

    public int getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(int minVersion) {
        this.minVersion = minVersion;
    }

    public int getMinAllowPatchVersion() {
        return minAllowPatchVersion;
    }

    public void setMinAllowPatchVersion(int minAllowPatchVersion) {
        this.minAllowPatchVersion = minAllowPatchVersion;
    }

    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getApkURL() {
        return apkURL;
    }

    public void setApkURL(String apkURL) {
        this.apkURL = apkURL;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public PatchInfoBean getPatchInfo() {
        return patchInfo;
    }

    public void setPatchInfo(PatchInfoBean patchInfo) {
        this.patchInfo = patchInfo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static class PatchInfoBean {
        /**
         * v100 : {"patchURL":"v100/100to101.patch","tip":"101 version(本次更新包大小:1114810byte)","hash":"ea97c8efa490a2eaf7d10b37e63dab0e","size":1114810}
         */

        private V100Bean v100;

        public V100Bean getV100() {
            return v100;
        }

        public void setV100(V100Bean v100) {
            this.v100 = v100;
        }

        public static class V100Bean {
            /**
             * patchURL : v100/100to101.patch
             * tip : 101 version(本次更新包大小:1114810byte)
             * hash : ea97c8efa490a2eaf7d10b37e63dab0e
             * size : 1114810
             */

            private String patchURL;
            private String tip;
            private String hash;
            private int size;

            public String getPatchURL() {
                return patchURL;
            }

            public void setPatchURL(String patchURL) {
                this.patchURL = patchURL;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }
}
