package com.crio.cred.service;

import com.crio.cred.entity.Vendor;
import com.crio.cred.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    /**
     * Add the vendor.
     *
     * @param vendorName the vendor name
     * @return the vendor
     */
    @Override
    public Vendor addVendor(String vendorName) {
        logger.trace("Entered addVendor");
        Vendor vendor = new Vendor();
        vendor.setVendor(vendorName);
        Vendor savedVendor = vendorRepository.save(vendor);
        logger.debug("Saved vendor id: " + savedVendor.getVendorId());
        logger.trace("Exited addVendor");
        return savedVendor;
    }

    /**
     * Get or add the vendor.
     *
     * @param vendorName the vendor name
     * @return the vendor
     */
    @Override
    public Vendor getOrAddVendor(String vendorName) {
        logger.trace("Entered getOrAddVendor");
        Optional<Vendor> vendorByName = getVendorByName(vendorName);
        if (vendorByName.isPresent()) {
            logger.trace("Exited getOrAddVendor");
            return vendorByName.get();
        }
        Vendor vendor = addVendor(vendorName);
        logger.trace("Exited getOrAddVendor");
        return vendor;
    }

    /**
     * Gets vendor by name.
     *
     * @param vendorName the vendor name
     * @return the vendor by name
     */
    @Override
    public Optional<Vendor> getVendorByName(String vendorName) {
        return vendorRepository.getVendorByVendor(vendorName);
    }

    /**
     * Gets vendor by id.
     *
     * @param id the vendor id
     * @return the vendor by id
     */
    @Override
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }
}
