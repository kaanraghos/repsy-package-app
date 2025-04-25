package com.baser.entity;

import jakarta.persistence.*;

@Entity
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String _package; // 'package' Java'da keyword, bu yüzden alt çizgiyle değiştiriyoruz
    private String version;

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
