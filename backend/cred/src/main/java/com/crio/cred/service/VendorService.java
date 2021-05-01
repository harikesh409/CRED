package com.crio.cred.service;

import com.crio.cred.entity.Vendor;

import java.util.Optional;

/**
 * The interface Vendor service.
 *
 * @author harikesh.pallantla
 */
public interface VendorService {
    /**
     * Add the vendor.
     *
     * @param vendorName the vendor name
     * @return the vendor
     */
    Vendor addVendor(String vendorName);

    /**
     * Get or add the vendor.
     *
     * @param vendorName the vendor name
     * @return the vendor
     */
    Vendor getOrAddVendor(String vendorName);

    /**
     * Gets vendor by name.
     *
     * @param vendorName the vendor name
     * @return the vendor by name
     */
    Optional<Vendor> getVendorByName(String vendorName);

    /**
     * Gets vendor by id.
     *
     * @param id the vendor id
     * @return the vendor by id
     */
    Optional<Vendor> getVendorById(Long id);
}
