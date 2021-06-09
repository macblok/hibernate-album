
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main demo = new Main();
		demo.addNewData();
//		demo.query1();	// Listing
//		System.out.println("===================================================================================");
//		demo.query2();	// Delete photo
//		System.out.println("===================================================================================");
//		demo.query3();	// Delete album
//		System.out.println("===================================================================================");
//		demo.query4();	// Delete user
//		System.out.println("===================================================================================");
//		demo.query5(); // Delete likes
//		System.out.println("===================================================================================");
//		demo.query6();
//		demo.query1();
		demo.close();

	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void query1() {
		String hql = "FROM User";
		Query query = session.createQuery(hql);
		List<User> results = query.list();

		for (User user : results) {

			System.out.print(user.getUserName() + " jest znajomym : ");
			for (User user1 : user.getFriends()) {
				System.out.print(user1.getUserName() + " ");
			}
			System.out.println();

			for (Album album : user.getUserAlbums()) {
				System.out.println("Album " + album.getName() + " u¿ytkownika " + user.getUserName() + ": "
						+ album.getDescription());

				for (Photo photo : album.getPhotos()) {
					System.out.print("		Zdjêcie " + photo.getName() + ", polubienia: ");

					for (User user1 : photo.getLikes()) {
						System.out.print(user1.getUserName() + " ");
					}
					System.out.println();
				}
				System.out.println();
			}

		}

	}

	private void query2() {
		String hql = "FROM Photo photo WHERE photo.name='Grecja 1'";
		Query query = session.createQuery(hql);
		List<Photo> results = query.list();
		Transaction transaction = session.beginTransaction();

		for (Photo photo : results) {
			session.delete(photo);
		}
		transaction.commit();
	}

	private void query3() {
		String hql = "FROM Album album WHERE album.name='Zwierzeta'";
		Query query = session.createQuery(hql);
		List<Album> results = query.list();
		Transaction transaction = session.beginTransaction();

		for (Album album : results) {
			session.delete(album);
		}
		transaction.commit();
	}

	private void query4() {
		String hql = "FROM User user WHERE user.userName='Maciek'";
		Query query = session.createQuery(hql);
		List<User> results = query.list();
		Transaction transaction = session.beginTransaction();

		for (User user : results) {
			session.delete(user);
		}
		transaction.commit();
	}

	private void query5() {
		String hql = "FROM User user WHERE user.userName='Agata'";
		Query query = session.createQuery(hql);
		List<User> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (User user : results) {

			Photo[] photos = new Photo[user.getLikedPhotos().size()];
			int i = 0;

			for (Photo photo : user.getLikedPhotos()) {
				photos[i] = photo;
				i++;
			}

			for (Photo photo : photos) {
				photo.removeLike(user);
			}
		}

		transaction.commit();

	}

	private void query6() {
		String hql = "From User user WHERE user.userName='Daniel'";
		Query query = session.createQuery(hql);
		List<User> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (User user : results) {
			
			User[] friends = new User[user.getFriends().size()];
			int i = 0;
			
			for (User user1 : user.getFriends()) {
				friends[i] = user1;
				i++;
			}
			
			for (User user2 : friends) {
				user.removeFriend(user2);
			}
			
		}

		transaction.commit();
	}

	private void addNewData() {
		User maciek = new User("Maciek");

		Album wakacje = new Album("Wakacje", "Zdjecia z wakacji", maciek);
		Album gory = new Album("Gory", "Zdjecia z gor", maciek);

		Photo chorwacja1 = new Photo("Chorwacja 1", maciek);
		Photo chorwacja2 = new Photo("Chorwacja 2", maciek);
		Photo grecja1 = new Photo("Grecja 1", maciek);
		Photo grecja2 = new Photo("Grecja 2", maciek);
		Photo grecja3 = new Photo("Grecja 3", maciek);
		Photo babiaGora = new Photo("Babia Gora", maciek);
		Photo turbacz = new Photo("Turbacz", maciek);

		wakacje.addPhoto(chorwacja1);
		wakacje.addPhoto(chorwacja2);
		wakacje.addPhoto(grecja1);
		wakacje.addPhoto(grecja2);
		wakacje.addPhoto(grecja3);
		gory.addPhoto(babiaGora);
		gory.addPhoto(turbacz);

		User agata = new User("Agata");

		Album zwierzeta = new Album("Zwierzeta", "Moje zwierzeta", agata);
		Album zamki = new Album("Zamki", "Zdjecia z wycieczek po zamkach", agata);

		Photo pies1 = new Photo("Pies 1", agata);
		Photo pies2 = new Photo("Pies 2", agata);
		Photo kot1 = new Photo("Kot 1", agata);
		Photo kot2 = new Photo("Kot 2", agata);
		Photo ogrodzieniec1 = new Photo("Ogrodzieniec 1", agata);
		Photo ogrodzieniec2 = new Photo("Ogrodzieniec 2", agata);
		Photo wawel1 = new Photo("Wawel 1", agata);
		Photo wawel2 = new Photo("Wawel 2", agata);

		zwierzeta.addPhoto(pies1);
		zwierzeta.addPhoto(pies2);
		zwierzeta.addPhoto(kot1);
		zwierzeta.addPhoto(kot2);
		zamki.addPhoto(ogrodzieniec1);
		zamki.addPhoto(ogrodzieniec2);
		zamki.addPhoto(wawel1);
		zamki.addPhoto(wawel2);

		User daniel = new User("Daniel");

		Album samochody = new Album("Samochody", "Ciekawe samochody", daniel);
		Album kosmos = new Album("Kosmos", "Zdjecia obiektów w kosmosie", daniel);

		Photo mustang = new Photo("Ford Mustang", daniel);
		Photo corvette = new Photo("Chevrolet Corvette", daniel);
		Photo carrera = new Photo("Porsche Carrera", daniel);
		Photo nebula = new Photo("Nebula", daniel);
		Photo mars = new Photo("Mars", daniel);
		Photo neptun = new Photo("Neptun", daniel);
		Photo merkury = new Photo("Mercury", daniel);
		Photo ziemia = new Photo("Ziemia", daniel);

		samochody.addPhoto(mustang);
		samochody.addPhoto(corvette);
		samochody.addPhoto(carrera);
		kosmos.addPhoto(nebula);
		kosmos.addPhoto(mars);
		kosmos.addPhoto(neptun);
		kosmos.addPhoto(merkury);
		kosmos.addPhoto(ziemia);

		User kaska = new User("Kaska");

		Album przyroda = new Album("Przyroda", "Zdjecia przyrody z wycieczek", kaska);

		Photo swierk = new Photo("Œwierk", kaska);
		Photo brzoza = new Photo("Brzoza", kaska);
		Photo motyl = new Photo("Motyl", kaska);
		Photo niedzwiedz = new Photo("Niedzwiedz", kaska);

		przyroda.addPhoto(swierk);
		przyroda.addPhoto(brzoza);
		przyroda.addPhoto(motyl);
		przyroda.addPhoto(niedzwiedz);

		maciek.addFriend(agata);
		maciek.addFriend(daniel);
		maciek.addFriend(kaska);
		agata.addFriend(daniel);
		agata.addFriend(kaska);

		grecja1.addLike(maciek);
		grecja1.addLike(agata);
		grecja1.addLike(daniel);
		grecja1.addLike(kaska);
		babiaGora.addLike(agata);
		babiaGora.addLike(daniel);
		babiaGora.addLike(kaska);
		pies2.addLike(maciek);
		pies2.addLike(agata);
		pies2.addLike(daniel);
		wawel1.addLike(maciek);
		wawel1.addLike(kaska);
		wawel1.addLike(daniel);
		mustang.addLike(maciek);
		mustang.addLike(agata);
		mustang.addLike(daniel);
		mustang.addLike(kaska);
		corvette.addLike(maciek);
		corvette.addLike(agata);
		nebula.addLike(agata);
		mars.addLike(daniel);
		mars.addLike(agata);
		ziemia.addLike(agata);
		brzoza.addLike(maciek);
		brzoza.addLike(agata);
		brzoza.addLike(kaska);
		niedzwiedz.addLike(maciek);
		niedzwiedz.addLike(agata);
		niedzwiedz.addLike(daniel);

		Transaction transaction = session.beginTransaction();
		session.save(maciek);
		session.save(agata);
		session.save(daniel);
		session.save(kaska);
		transaction.commit();

	}

}
