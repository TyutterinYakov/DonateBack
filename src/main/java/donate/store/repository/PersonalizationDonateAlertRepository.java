package donate.store.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.store.entity.WidgetEntity;
import donate.store.entity.UserEntity;

@Repository
public interface PersonalizationDonateAlertRepository extends JpaRepository<WidgetEntity, Long>{

	Set<WidgetEntity> findByUser(UserEntity user);

	void deleteByPersonalizationIdAndUser(Long personalizationId, UserEntity user);

	Set<WidgetEntity> findAllByUser(UserEntity user);

	Optional<WidgetEntity> findByPersonalizationIdAndUser(Long widgetId, UserEntity user);

}
