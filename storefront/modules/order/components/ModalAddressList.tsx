import AddressCard from '@/modules/address/components/AddressCard';
import { Address } from '@/modules/address/models/AddressModel';
import { getUserAddress } from '@/modules/customer/services/CustomerService';
import React, { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';

type Props = {
  showModal: boolean;
  handleClose: () => void;
  handleSelectAddress: (address: Address) => any;
  defaultUserAddress?: Address;
};

const ModalAddressList = ({
  showModal,
  handleClose,
  handleSelectAddress,
  defaultUserAddress,
}: Props) => {
  const [addresses, setAddresses] = useState<Address[]>([]);
  const [selectedAddressId, setSelectedAddressId] = useState<number>();

  useEffect(() => {
    getUserAddress()
      .then((res) => {
        setAddresses(res);

        if (defaultUserAddress?.id) {
          setSelectedAddressId(defaultUserAddress.id);
        }
      })
      .catch((err) => {
        console.log('Load address fail: ', err.message);
      });
  }, [defaultUserAddress]);

  const handleAddressClick = (address: Address) => {
    setSelectedAddressId(address.id);
    handleSelectAddress(address);
    handleClose();
  };

  return (
    <Modal show={showModal} onHide={handleClose} size="lg" centered>
      <Modal.Header closeButton>
        <Modal.Title className="text-dark fw-bold">Select address</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ minHeight: '500px' }}>
        <div className="body">
          <div className="row">
            {addresses.length == 0 ? (
              <div className="mx-2">Please add your address in your profile</div>
            ) : (
              addresses.map((address) => (
                <div
                  className="col-lg-6 mb-2"
                  onClick={() => {
                    handleAddressClick(address);
                  }}
                  key={address.id}
                >
                  <AddressCard address={address} isSelected={selectedAddressId == address.id} />
                </div>
              ))
            )}
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default ModalAddressList;
